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
      try {
        Optional<User> user = userDao.findById(id);
        logger.info("Row: {}", user.orElse(null));
        return user;
      } catch(Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollback();
        throw new DBServiceException(e);
      }

    }
  }

  @Override
  public long saveUser(User user) {
    try(SessionManager sessionManager = userDao.getSessionManger()) {
      sessionManager.open();
      try {
      long id = userDao.saveUser(user);
      sessionManager.commit();
      logger.info("created " + ": {}", id);
      return id;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollback();
        throw new DBServiceException(e);
      }
    }
  }
}
