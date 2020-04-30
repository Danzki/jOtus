package com.danzki.backend.generators;

public interface MongoGenerator {
  void generateUser(String name, int age, String login, String password);
  void generateUsers();
}
