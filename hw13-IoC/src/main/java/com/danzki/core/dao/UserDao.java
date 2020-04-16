package com.danzki.core.dao;

import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserDao {
  SessionManager getSessionManager();

  ObjectId create(User user);
  User load(ObjectId id);
  User findByLogin(String login);
  List<User> findAll();
}
