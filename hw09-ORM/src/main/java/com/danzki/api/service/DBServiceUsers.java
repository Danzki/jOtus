package com.danzki.api.service;

import com.danzki.api.dao.UserDao;
import com.danzki.api.model.User;
import com.danzki.api.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DBServiceUsers implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DBServiceUsers.class);

  private final UserDao userDao;

  public DBServiceUsers(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<User> getUser(long id) {
    try(SessionManager sessionManager = userDao.getSessionManger()) {
      sessionManager.open();

    }
  }

  @Override
  public long saveUser(User user) {
    try(SessionManager sessionManager = userDao.getSessionManger()) {
      sessionManager.open();
      long id = userDao.saveUser(user);
      sessionManager.commit();
      logger.info("created " + : {}", id);
      return id;
    }
  }
}
