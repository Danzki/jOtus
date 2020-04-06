package com.danzki.mongo.template;

public interface MongoGenerator {
  void generateUser(String name, int age, String login, String password);
  void generateUsers();
}
