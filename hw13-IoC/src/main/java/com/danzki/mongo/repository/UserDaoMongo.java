package com.danzki.mongo.repository;


import com.danzki.core.dao.UserDao;
import com.danzki.core.dao.UserDaoException;
import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.mongo.sessionmanager.SessionManagerMongo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoMongo implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoMongo.class);

  @Autowired
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
      var mongoClient = sessionManager.getCurrentSession().getMongoClient();
      var dataBaseName = sessionManager.getDatabaseName();
      var mongoTemplate = new MongoTemplate(mongoClient, dataBaseName);
      var user1 = mongoTemplate.insert(user);
      sessionManager.close();
      return user1.get_id();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public Optional<User> load(ObjectId id) {
    try {
      var mongoClient = sessionManager.getCurrentSession().getMongoClient();
      var dataBaseName = sessionManager.getDatabaseName();
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").lt(id)).limit(1);
      var mongoTemplate = new MongoTemplate(mongoClient, dataBaseName);
      return Optional.of(mongoTemplate.findOne(query, User.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> findByLogin(String login) {
    try {
      var mongoClient = sessionManager.getCurrentSession().getMongoClient();
      var dataBaseName = sessionManager.getDatabaseName();
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").lt(login)).limit(1);
      var mongoTemplate = new MongoTemplate(mongoClient, dataBaseName);
      return Optional.of(mongoTemplate.findOne(query, User.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    try {
      var mongoClient = sessionManager.getCurrentSession().getMongoClient();
      var dataBaseName = sessionManager.getDatabaseName();
      var mongoTemplate = new MongoTemplate(mongoClient, dataBaseName);
      return mongoTemplate.findAll(User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
