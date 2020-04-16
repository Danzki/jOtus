package com.danzki.generators;

import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MongoGeneratorImpl implements MongoGenerator{
  private static Logger logger = LoggerFactory.getLogger(MongoGeneratorImpl.class);
  private DBServiceUser dbServiceUser;

  public MongoGeneratorImpl(DBServiceUser dbServiceUser) {
    this.dbServiceUser = dbServiceUser;
  }

  @Override
  @Bean
  public void generateUsers() {
    logger.info("generateUsers");
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
