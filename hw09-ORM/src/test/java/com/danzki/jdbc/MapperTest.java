package com.danzki.jdbc;

import com.danzki.core.model.Account;
import com.danzki.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JDBS Mapper test")
public class MapperTest {
  private static Logger logger = LoggerFactory.getLogger(MapperTest.class);

  Mapper mapper;
  User user;
  Account account;

  @BeforeEach
  public void initialte() {
    user = new User.UserBuilder()
        .withId(1)
        .withName("Vasiliy")
        .withAge(25)
        .build();

    account = new Account.AccountBuilder()
        .withNo(1)
        .withType("Current")
        .withRest(30)
        .build();
  }

  @Test
  @DisplayName("Create User Test")
  public void createUserTest() {
    mapper = new Mapper(User.class);
    String expected = "insert into User(name,age) values (?,?)";
    String actual = mapper.getInsertQuery();
    logger.info("expected: " + expected);
    logger.info("actual  : " + actual);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Update User Test")
  public void updateUserTest() throws IllegalAccessException {
    mapper = new Mapper(User.class);
    String expected = "update User set name = 'Vasiliy',age = 25 where id = ?";
    String actual = mapper.getUpdateQuery(user);
    logger.info("expected: " + expected);
    logger.info("actual: " + actual);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Select User Test")
  public void selectUserTest() {
    mapper = new Mapper(User.class);
    String expected = "select id, name, age from User where id = ?";
    String actual = mapper.getSelectQuery();
    logger.info("expected: " + expected);
    logger.info("actual: " + actual);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Create Account Test")
  public void createAccountTest() {
    mapper = new Mapper(Account.class);
    String expected = "insert into Account(type,rest) values (?,?)";
    String actual = mapper.getInsertQuery();
    logger.info("expected: " + expected);
    logger.info("actual  : " + actual);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Update Account Test")
  public void updateAccountTest() throws IllegalAccessException {
    mapper = new Mapper(Account.class);
    String expected = "update Account set type = 'Current',rest = 30 where no = ?";
    String actual = mapper.getUpdateQuery(account);
    logger.info("expected: " + expected);
    logger.info("actual: " + actual);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Select Account Test")
  public void selectAccountTest() {
    mapper = new Mapper(Account.class);
    String expected = "select no, type, rest from Account where no = ?";
    String actual = mapper.getSelectQuery();
    logger.info("expected: " + expected);
    logger.info("actual: " + actual);
    assertEquals(expected, actual);
  }
}
