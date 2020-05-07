package com.danzki.backend.mongo.repository;


import com.danzki.core.dao.UserDao;
import com.danzki.core.dao.UserDaoException;
import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.configuration.MongoConfig;
import com.mongodb.client.MongoClients;
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

@Repository
public class UserDaoMongo implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoMongo.class);

  @Autowired
  private MongoConfig mongoConfig;

  public UserDaoMongo(MongoConfig mongoConfig) {
    this.mongoConfig = mongoConfig;
  }

  @Override
  public SessionManager getSessionManager() {
    return null;
  }

  @Override
  public ObjectId create(User user) {
    try {
      var mongoTemplate = new MongoTemplate(MongoClients.create(mongoConfig.getUrl()), mongoConfig.getDatabaseName());
      var user1 = mongoTemplate.insert(user);
      return user1.get_id();
    } catch (Exception e) {
      throw new UserDaoException(e);
    }
  }

  @Override
  public User load(ObjectId id) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").lt(id)).limit(1);
      var mongoTemplate = new MongoTemplate(MongoClients.create(mongoConfig.getUrl()), mongoConfig.getDatabaseName());
      return mongoTemplate.findOne(query, User.class);
    } catch (Exception e) {
      logger.info(e.toString());
    }
    return null;
  }

  @Override
  public User findByLogin(String login) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").lt(login)).limit(1);
      var mongoTemplate = new MongoTemplate(MongoClients.create(mongoConfig.getUrl()), mongoConfig.getDatabaseName());
      return mongoTemplate.findOne(query, User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<User> findAll() {
    try {
      var mongoTemplate = new MongoTemplate(MongoClients.create(mongoConfig.getUrl()), mongoConfig.getDatabaseName());
      return mongoTemplate.findAll(User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  @Override
  public void dropDatabase() {
    MongoClients.create(mongoConfig.getUrl()).getDatabase(mongoConfig.getDatabaseName()).drop();
  }
}
