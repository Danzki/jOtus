package com.danzki.core.service;


import com.danzki.core.model.User;

import java.util.Optional;

public interface DBServiceUser {
  long create(User user);
  void update(User user);
  Optional<User> load(long id);
}
