package com.danzki;

import com.danzki.core.dao.UserDao;
import com.danzki.core.model.AddressDataSet;
import com.danzki.core.model.PhoneDataSet;
import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.mongo.dao.UserDaoMongo;
import com.danzki.mongo.sessionmanager.SessionManagerMongo;
import com.danzki.mongo.template.MongoGenerator;
import com.danzki.mongo.template.MongoGeneratorImpl;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceMongoApp {
  private static Logger logger = LoggerFactory.getLogger(DbServiceMongoApp.class);

  public static void main(String[] args) {
    SessionManagerMongo sessionManager = new SessionManagerMongo("mongodb://localhost", "mongo-db-test");
    sessionManager.dropDatabase();
    UserDao userDao = new UserDaoMongo(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
    MongoGenerator mongoGenerator = new MongoGeneratorImpl(dbServiceUser);

    var user = new User( "UserName", 30, "user1", "11111");

    var address = new AddressDataSet();
    address.setUser(user);
    address.setStreet("User's Street, 15");

    var phones = new ArrayList<PhoneDataSet>();
    phones.add(new PhoneDataSet.PhoneDataSetBuilder()
        .withNumber("12345")
        .withUser(user)
        .build()
    );
    phones.add(new PhoneDataSet.PhoneDataSetBuilder()
        .withNumber("54321")
        .withUser(user)
        .build()
    );
    phones.add(new PhoneDataSet.PhoneDataSetBuilder()
        .withNumber("45636")
        .withUser(user)
        .build()
    );

    user.setAddressDataSet(address);
    user.setPhones(phones);

    logger.info("Step 1. Create new User");
    ObjectId id = dbServiceUser.create(user);
    user.set_id(id);
    logger.info("Created user: " + user + " with _id=" + id);
    Optional<User> mayBeCreatedUser = dbServiceUser.load(id);
    mayBeCreatedUser.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}, age:{}", crUser.getName(), crUser.getAge()),
        () -> logger.info("user was not found")
    );

    logger.info("Step 2. Find user by login");
    Optional<User> mayBeCreatedUser2 = dbServiceUser.findByLogin(user.getLogin());
    mayBeCreatedUser2.ifPresentOrElse(
        crUser -> logger.info("Found user, name:{}, age:{}, login:{}", crUser.getName(), crUser.getAge(), crUser.getLogin()),
        () -> logger.info("user was not found")
    );

    logger.info("Step 3. Find all users");
    List<User> users = dbServiceUser.findAll();
    for(User user1 : users) {
      logger.info("User{ name:{}, age:{}, login:{}}", user1.getName(), user1.getAge(), user1.getLogin());
    }
  }
}
