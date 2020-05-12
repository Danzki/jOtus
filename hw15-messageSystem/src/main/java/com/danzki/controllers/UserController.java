package com.danzki.controllers;

import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.front.FrontendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  private final DBServiceUser dbServiceUser;
  private final FrontendService frontendService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public UserController(DBServiceUser dbServiceUser, FrontendService frontendService, SimpMessagingTemplate simpMessagingTemplate) {
    this.dbServiceUser = dbServiceUser;
    this.frontendService = frontendService;
    this.simpMessagingTemplate = simpMessagingTemplate;
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

  @MessageMapping("/userSaveMessage")
  public void userSave(String message) {
    logger.info("Message from Frontend: " + message);
    frontendService.saveUserData(message, userData-> {
      logger.info("DB service response: " + userData);
      simpMessagingTemplate.convertAndSend("/topic/DBServiceResponse", userData);
    });
  }


}
