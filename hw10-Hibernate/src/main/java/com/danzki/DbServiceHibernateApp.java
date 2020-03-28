package com.danzki;

import com.danzki.core.model.AddressDataSet;
import com.danzki.core.model.PhoneDataSet;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.danzki.core.dao.UserDao;
import com.danzki.hibernate.dao.UserDaoHibernate;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.core.model.User;
import com.danzki.hibernate.HibernateUtils;
import com.danzki.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Optional;

public class DbServiceHibernateApp {
  private static Logger logger = LoggerFactory.getLogger(DbServiceHibernateApp.class);

  public static void main(String[] args) {
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
        User.class, AddressDataSet.class, PhoneDataSet.class);

    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

    var user = new User(0, "UserName", 30);

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

    user.setPhones(phones);
    user.setAddressDataSet(address);

    logger.info("Step 1. Create new User");
    long id = dbServiceUser.create(user);
    logger.info("Created user: " + user + " with id=" + id);
    Optional<User> mayBeCreatedUser = dbServiceUser.load(id);
    mayBeCreatedUser.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}, age:{}", crUser.getName(), crUser.getAge()),
        () -> logger.info("user was not created")
    );

    user.setName("Normal Name");
    user.setAge(33);
    logger.info("Step 2. Create new User");
    dbServiceUser.update(user);
    logger.info("Updated user: " + user + " with id=" + id);
  }
}
