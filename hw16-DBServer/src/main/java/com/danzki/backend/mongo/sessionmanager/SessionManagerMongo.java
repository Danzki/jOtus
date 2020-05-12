package com.danzki.backend.mongo.sessionmanager;

import com.danzki.configuration.MongoConfig;
import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.core.sessionmanager.SessionManagerException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManagerMongo implements SessionManager {

  @Autowired
  MongoConfig mongoConfig;

  private MongoDatabase database;
  private DatabaseSessionMongo databaseSession;

  public SessionManagerMongo() {
  }

  public MongoDatabase getDatabase() {
    return database;
  }

  public void dropDatabase() {
    try {
      databaseSession = new DatabaseSessionMongo(MongoClients.create(mongoConfig.getUrl()));
      database = databaseSession.getMongoClient().getDatabase(mongoConfig.getDatabaseName());
      database.drop();
    }  catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  public String getDatabaseName() {
    return mongoConfig.getDatabaseName();
  }

  @Override
  public void beginSession() {
    try {
      databaseSession = new DatabaseSessionMongo(MongoClients.create(mongoConfig.getUrl()));
      database = databaseSession.getMongoClient().getDatabase(mongoConfig.getDatabaseName());
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void commitSession() {

  }

  @Override
  public void rollbackSession() {

  }

  @Override
  public void close() {
    databaseSession.close();
  }

  @Override
  public DatabaseSessionMongo getCurrentSession() {
    return databaseSession;
  }
}
