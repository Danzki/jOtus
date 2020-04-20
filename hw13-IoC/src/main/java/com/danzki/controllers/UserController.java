package com.danzki.controllers;

import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.generators.MongoGenerator;
import com.danzki.generators.MongoGeneratorImpl;
import com.danzki.mongo.repository.UserDaoMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class UserController {
  private static Logger logger = LoggerFactory.getLogger(UserDaoMongo.class);

  @Autowired
  private final DBServiceUser dbServiceUser;

  public UserController(DBServiceUser dbServiceUser) {
    this.dbServiceUser = dbServiceUser;
  }

  @PostConstruct
  public void loadData() {
    MongoGenerator mongoGenerator = new MongoGeneratorImpl(dbServiceUser);
    mongoGenerator.generateUsers();
    logger.info("loadData: Users generated.");
  }

  @GetMapping("/user/list")
  public String userListView(Model model) {
    List<User> users = dbServiceUser.findAll();
    logger.info("userListView: Found " + users.size() + " users.");
    model.addAttribute("users", users);
    return "userList";
  }

  @GetMapping("/user/create")
  public String userCreateView(Model model) {
    model.addAttribute("user", new User());
    return "create";
  }

  @PostMapping("/user/save")
  public RedirectView userSave(@ModelAttribute User user) {
    logger.info("/user/save: " + user);
    dbServiceUser.create(user);
    return new RedirectView("/user/list", true);
  }

}
