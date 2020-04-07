package com.danzki.core.service;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ObjectId create(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        ObjectId userId = userDao.create(user);
        sessionManager.close();
        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw new DbServiceException(e);
      }

    }
  }

  @Override
  public Optional<User> load(ObjectId id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.load(id);

        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> findByLogin(String login) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findByLogin(login);
        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
      return Optional.empty();
    }
  }

  @Override
  public List<User> findAll() {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        List<User> users = userDao.findAll();
        logger.info("usersSize: {}", users.size());
        return users;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
      return new ArrayList<>();
    }
  }
}
