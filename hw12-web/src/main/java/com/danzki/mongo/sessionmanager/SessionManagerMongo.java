package com.danzki.mongo.sessionmanager;

import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.core.sessionmanager.SessionManagerException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class SessionManagerMongo implements SessionManager {

  private MongoDatabase database;
  private DatabaseSessionMongo databaseSession;
  private String dbUrl;
  private String dbName;

  public SessionManagerMongo(String dbUrl, String dbName) {
    this.dbUrl = dbUrl;
    this.dbName = dbName;
  }

  public MongoDatabase getDatabase() {
    return database;
  }

  public void dropDatabase() {
    try {
      databaseSession = new DatabaseSessionMongo(MongoClients.create(this.dbUrl));
      database = databaseSession.getMongoClient().getDatabase(this.dbName);
      database.drop();
    }  catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public String getDbName() {
    return dbName;
  }

  @Override
  public void beginSession() {
    try {
      databaseSession = new DatabaseSessionMongo(MongoClients.create(this.dbUrl));
      database = databaseSession.getMongoClient().getDatabase(this.dbName);
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
