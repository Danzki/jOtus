package com.danzki.backend.mongo.sessionmanager;

import com.danzki.core.sessionmanager.DatabaseSession;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSessionMongo implements DatabaseSession {
  @Autowired
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
