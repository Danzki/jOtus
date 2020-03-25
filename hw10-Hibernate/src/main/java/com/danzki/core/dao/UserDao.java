package com.danzki.core.dao;

import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  SessionManager getSessionManager();

  long create(User user);
  void update(User user);
  Optional<User> load(long id);
}
