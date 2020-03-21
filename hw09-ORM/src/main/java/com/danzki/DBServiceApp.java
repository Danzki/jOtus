package com.danzki;

import com.danzki.core.dao.AccountDao;
import com.danzki.core.dao.UserDao;
import com.danzki.core.model.Account;
import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceAccount;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceAccountImpl;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.h2.DataSourceH2;
import com.danzki.jdbc.DbExecutor;
import com.danzki.jdbc.Mapper;
import com.danzki.jdbc.dao.AccountDaoJdbc;
import com.danzki.jdbc.dao.UserDaoJdbc;
import com.danzki.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DBServiceApp {
  private static Logger logger = LoggerFactory.getLogger(DBServiceApp.class);

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();
    DBServiceApp demo = new DBServiceApp();

    dealWithUser(dataSource, demo);
    dealWithAccount(dataSource, demo);
  }

  private static void dealWithUser(DataSource dataSource, DBServiceApp demo) throws SQLException {
    demo.createUserTable(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<User> dbExecutor = new DbExecutor<>();
    Mapper<User> mapper = new Mapper<>(User.class);
    UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor, mapper);

    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
    long id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 33));
    Optional<User> user = dbServiceUser.getUser(id);

    logger.info(String.valueOf(user));
    user.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}, age:{}", crUser.getName(), crUser.getAge()),
        () -> logger.info("user was not created")
    );

    User createdUser = user.orElse(user.get());
    createdUser.setName("Normal Name");
    createdUser.setAge(30);
    dbServiceUser.update(createdUser);

    logger.info(String.valueOf(user));
  }

  /*
  • id bigint(20) NOT NULL auto_increment
  • name varchar(255)
  • age int(3)
   */
  private void createUserTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table user(id long not null auto_increment, name varchar(255), age int)")) {
      pst.executeUpdate();
    }
    logger.info("table created");
  }

  private static void dealWithAccount(DataSource dataSource, DBServiceApp demo) throws SQLException {
    demo.createAccountTable(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<Account> dbExecutor = new DbExecutor<>();
    Mapper<Account> mapper = new Mapper<>(Account.class);
    AccountDao accountDao = new AccountDaoJdbc(sessionManager, dbExecutor, mapper);

    DBServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);
    long id = dbServiceAccount.saveAccount(new Account(0, "Current", 0));
    Optional<Account> account = dbServiceAccount.getAccount(id);

    logger.info(String.valueOf(account));
    account.ifPresentOrElse(
        crAccount -> logger.info("created account, type:{}, rest:{}", crAccount.getType(), crAccount.getRest()),
        () -> logger.info("account was not created")
    );

    Account createdAccount = account.orElse(account.get());
    createdAccount.setType("Transit");
    createdAccount.setRest(30);
    dbServiceAccount.update(createdAccount);

    logger.info(String.valueOf(account));
  }

  /*
  • no bigint(20) NOT NULL auto_increment
  • type varchar(255)
  • rest number
   */
  private void createAccountTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table account(no long not null auto_increment, type varchar(255), rest int)")) {
      pst.executeUpdate();
    }
    logger.info("table created");
  }
}
