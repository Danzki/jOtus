package com.danzki.core.service;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  @Autowired
  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ObjectId create(User user) {
    ObjectId userId = userDao.create(user);
    logger.info("created user: {}", userId);
    return userId;
  }

  @Override
  public User load(ObjectId id) {
    User userOptional = userDao.load(id);
    logger.info("user: {}", userDao);
    return userOptional;
  }

  @Override
  public User findByLogin(String login) {
    User userOptional = userDao.findByLogin(login);
    logger.info("user: {}", userOptional);
    return userOptional;
  }

  @Override
  public List<User> findAll() {
    List<User> users = userDao.findAll();
    logger.info("usersSize: {}", users.size());
    return users;
  }
}
