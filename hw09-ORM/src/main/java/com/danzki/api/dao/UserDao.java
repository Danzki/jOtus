package com.danzki.api.dao;

import com.danzki.api.model.User;
import com.danzki.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  long saveUser(User user);
  Optional<User> findById(long id);
  SessionManager getSessionManger();
}
