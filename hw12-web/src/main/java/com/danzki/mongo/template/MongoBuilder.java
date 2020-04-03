package com.danzki.mongo.template;

import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.services.PasswordService;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

public class MongoBuilder {

  private static final String MONGODB_URL = "mongodb://localhost"; // Работа без DockerToolbox
  private static final String MONGO_DATABASE_NAME = "mongo-db-test";

  private MongoDatabase database;

  public MongoBuilder() {
    try (var mongoClient = MongoClients.create(MONGODB_URL)) {
      database = mongoClient.getDatabase(MONGO_DATABASE_NAME);
      database.drop();
    }
  }

  public void generateUsers(DBServiceUser dbServiceUser) {
    String password = new PasswordService().generatePassword();
    generateUser(dbServiceUser, "Admin", 50, "admin", "admin");
    generateUser(dbServiceUser, "Крис Гир", 30, "user1", password);
    generateUser(dbServiceUser,"Ая Кэш", 30, "user2", password);
    generateUser(dbServiceUser,"Десмин Боргес", 30, "user3", password);
  }

  private void generateUser(DBServiceUser dbServiceUser, String name, int age, String login, String password) {
    User user = new User.UserBuilder()
        .withName(name)
        .withAge(age)
        .withLogin(login)
        .withPassword(password)
        .build();

    ObjectId id = dbServiceUser.create(user);
    user.set_id(id);
  }
}
