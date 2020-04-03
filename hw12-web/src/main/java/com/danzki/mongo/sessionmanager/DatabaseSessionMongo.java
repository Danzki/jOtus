package com.danzki.mongo.sessionmanager;

import com.danzki.core.sessionmanager.DatabaseSession;
import com.mongodb.client.MongoClient;

public class DatabaseSessionMongo implements DatabaseSession {
  private final MongoClient mongoClient;

  public DatabaseSessionMongo(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public MongoClient getMongoClient() {
    return mongoClient;
  }

  public void close() {
    mongoClient.close();
  }
}
