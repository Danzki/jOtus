package com.danzki.core.service;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbServiceUserImpl implements DBServiceUser {

  @Autowired
  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ObjectId create(User user) {
    ObjectId userId = userDao.create(user);
    return userId;
  }

  @Override
  public User load(ObjectId id) {
    User userOptional = userDao.load(id);
    return userOptional;
  }

  @Override
  public User findByLogin(String login) {
    User userOptional = userDao.findByLogin(login);
    return userOptional;
  }

  @Override
  public List<User> findAll() {
    List<User> users = userDao.findAll();
    return users;
  }

  @Override
  public void dropDatabase() {
    userDao.findAll();
  }
}
