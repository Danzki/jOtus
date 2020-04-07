package com.danzki.core.service;


import com.danzki.core.model.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {
  ObjectId create(User user);
  Optional<User> load(ObjectId id);
  Optional<User> findByLogin(String login);
  List<User> findAll();
}
