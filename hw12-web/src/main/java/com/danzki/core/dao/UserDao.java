package com.danzki.core.dao;

import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  SessionManager getSessionManager();

  ObjectId create(User user);
  Optional<User> load(ObjectId id);
  Optional<User> findByLogin(String login);
  List<User> findAll();
}
