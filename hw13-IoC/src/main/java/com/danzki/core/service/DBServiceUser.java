package com.danzki.core.service;


import com.danzki.core.model.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface DBServiceUser {
  ObjectId create(User user);
  User load(ObjectId id);
  User findByLogin(String login);
  List<User> findAll();
  void dropDatabase();
}
