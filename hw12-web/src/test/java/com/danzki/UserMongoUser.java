package com.danzki;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.mongo.dao.UserDaoMongo;
import com.danzki.mongo.sessionmanager.SessionManagerMongo;
import com.danzki.mongo.template.MongoBuilder;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User class test")
public class UserMongoUser extends UserHelper {
  private static Logger logger = LoggerFactory.getLogger(UserMongoUser.class);

  private static final String MONGODB_URL = "mongodb://localhost"; // Работа без DockerToolbox
  private static final String MONGO_DATABASE_NAME = "mongo-db-test";
  private static final String USERS_COLLECTION = "users";

  private static MongoBuilder mongoBuilder;
  private static SessionManagerMongo sessionManager;
  private static UserDao userDao;
  private static DBServiceUser dbServiceUser;

  @BeforeEach
  void initialize() {
    try (var mongoClient = MongoClients.create(MONGODB_URL)) {
      MongoDatabase database = mongoClient.getDatabase(MONGO_DATABASE_NAME);
      database.drop();
    }
    sessionManager = new SessionManagerMongo();
    userDao = new UserDaoMongo(sessionManager);
    dbServiceUser = new DbServiceUserImpl(userDao);
  }

  @DisplayName(("User create and select"))
  @Test
  void shouldCorrectSaveUser() {
    User defaultUser = buildDefaultUser();
    logger.info("Test Step 1. Create new User");
    ObjectId id = dbServiceUser.create(defaultUser);
    defaultUser.set_id(id);
    logger.info("Created defaultUser: " + defaultUser + " with _id=" + id);
    Optional<User> mayBeCreatedUser = dbServiceUser.load(id);
    User loadedUser = mayBeCreatedUser.orElse(buildAnotherUser());
    assertThat(loadedUser).isEqualTo(defaultUser);
  }

  @DisplayName(("Find by login test"))
  @Test
  void shouldFindByLogin() {
    User defaultUser = buildDefaultUser();
    logger.info("Test Step 1. Create new User with login " + defaultUser.getLogin());
    ObjectId id = dbServiceUser.create(defaultUser);
    defaultUser.set_id(id);
    logger.info("Created defaultUser: " + defaultUser + " with login=" + defaultUser.getLogin());
    Optional<User> mayBeCreatedUser = dbServiceUser.findByLogin(defaultUser.getLogin());
    User loadedUser = mayBeCreatedUser.orElse(buildAnotherUser());
    assertThat(loadedUser).isEqualTo(defaultUser);
  }

}

