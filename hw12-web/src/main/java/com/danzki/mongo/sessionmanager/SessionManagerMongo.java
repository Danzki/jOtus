package com.danzki.mongo.sessionmanager;

import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.core.sessionmanager.SessionManagerException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class SessionManagerMongo implements SessionManager {
  private static final String MONGODB_URL = "mongodb://localhost"; // Работа без DockerToolbox
  private static final String MONGO_DATABASE_NAME = "mongo-db-test";

  private MongoDatabase database;
  private DatabaseSessionMongo databaseSession;

  public MongoDatabase getDatabase() {
    return database;
  }

  @Override
  public void beginSession() {
    try {
      databaseSession = new DatabaseSessionMongo(MongoClients.create(MONGODB_URL));
      database = databaseSession.getMongoClient().getDatabase(MONGO_DATABASE_NAME);
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
