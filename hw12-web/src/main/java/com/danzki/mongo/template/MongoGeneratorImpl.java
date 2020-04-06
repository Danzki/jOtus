package com.danzki.mongo.template;

import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.mongo.sessionmanager.SessionManagerMongo;
import com.danzki.services.PasswordService;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

public class MongoGeneratorImpl implements MongoGenerator{

  private SessionManagerMongo sessionManager;
  private DBServiceUser dbServiceUser;

  public MongoGeneratorImpl(SessionManagerMongo sessionManager, DBServiceUser dbServiceUser) {
    this.sessionManager = sessionManager;
    this.dbServiceUser = dbServiceUser;
    this.sessionManager.beginSession();
    MongoDatabase database = this.sessionManager.getDatabase();
    database.drop();
  }

  @Override
  public void generateUsers() {
    String password = new PasswordService().generatePassword();
    generateUser("Admin", 50, "admin", "admin");
    generateUser( "Крис Гир", 30, "user1", password);
    generateUser("Ая Кэш", 30, "user2", password);
    generateUser("Десмин Боргес", 30, "user3", password);
  }

  @Override
  public void generateUser(String name, int age, String login, String password) {
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
