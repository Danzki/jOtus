package com.danzki.servlet;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.services.PasswordService;
import com.danzki.services.TemplateProcessor;
import org.bson.types.ObjectId;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

public class CreateServlet extends HttpServlet {

  private static final String PARAM_NAME = "name";
  private static final String PARAM_AGE = "age";
  private static final String PARAM_LOGIN = "login";
  private static final int MAX_INACTIVE_INTERVAL = 30;
  private static final String CREATE_PAGE_TEMPLATE = "create.html";

  private final TemplateProcessor templateProcessor;
  private final UserDao userDao;
  private final DBServiceUser dbServiceUser;

  public CreateServlet(TemplateProcessor templateProcessor, UserDao userDao) {
    this.userDao = userDao;
    this.templateProcessor = templateProcessor;
    this.dbServiceUser = new DbServiceUserImpl(userDao);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(CREATE_PAGE_TEMPLATE, Collections.emptyMap()));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String name = request.getParameter(PARAM_NAME);
    int age = Integer.parseInt(request.getParameter(PARAM_AGE));
    String login = request.getParameter(PARAM_LOGIN);

    User user = new User.UserBuilder()
        .withName(name)
        .withAge(age)
        .withLogin(login)
        .withPassword(new PasswordService().generatePassword())
        .build();

    ObjectId id = dbServiceUser.create(user);
    user.set_id(id);

    HttpSession session = request.getSession();
    session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
    response.sendRedirect("/admin");
  }
}
