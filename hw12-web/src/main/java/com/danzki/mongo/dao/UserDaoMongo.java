package com.danzki.mongo.dao;


import com.danzki.core.dao.UserDao;
import com.danzki.core.dao.UserDaoException;
import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.mongo.sessionmanager.SessionManagerMongo;
import com.danzki.mongo.template.MongoTemplateImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class UserDaoMongo implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoMongo.class);

  private static final String USERS_COLLECTION_NAME = "users";
  private final SessionManagerMongo sessionManager;

  public UserDaoMongo(SessionManagerMongo sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  @Override
  public ObjectId create(User user) {
    try {
      sessionManager.beginSession();
      var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      var collection = sessionManager.getDatabase().getCollection(USERS_COLLECTION_NAME);
      var mongoTemplate = new MongoTemplateImpl(collection, mapper);
      var _id = mongoTemplate.insert(user);
      sessionManager.close();
      return _id;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public Optional<User> load(ObjectId id) {
    try {
      var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      var collection = sessionManager.getDatabase().getCollection(USERS_COLLECTION_NAME);
      var mongoTemplate = new MongoTemplateImpl(collection, mapper);
      return mongoTemplate.findOne(eq("_id", id), User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> findByLogin(String login) {
    try {
      var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      var collection = sessionManager.getDatabase().getCollection(USERS_COLLECTION_NAME);
      var mongoTemplate = new MongoTemplateImpl(collection, mapper);
      return mongoTemplate.findOne(eq("login", login), User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    try {
      var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      var collection = sessionManager.getDatabase().getCollection(USERS_COLLECTION_NAME);
      var mongoTemplate = new MongoTemplateImpl(collection, mapper);
      return mongoTemplate.findAll(User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
