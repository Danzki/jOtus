package com.danzki.servlet;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {

  private static final String USERS_LIST = "usersList";
  private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

  private final TemplateProcessor templateProcessor;
  private final UserDao userDao;
  private final DBServiceUser dbServiceUser;

  public AdminServlet(TemplateProcessor templateProcessor, UserDao userDao) {
    this.userDao = userDao;
    this.templateProcessor = templateProcessor;
    this.dbServiceUser = new DbServiceUserImpl(userDao);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<User> users = dbServiceUser.findAll();

    Map<String, Object> params = new HashMap<>();
    params.put(USERS_LIST, users);

    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, params));
  }
}
