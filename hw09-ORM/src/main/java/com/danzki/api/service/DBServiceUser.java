package com.danzki.api.service;

import com.danzki.api.model.User;

import java.util.Optional;

public interface DBServiceUser {
  Optional<User> getUser(long id);
  long saveUser(User user);
}
