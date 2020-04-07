package com.danzki;

import com.danzki.core.model.User;

public class UserHelper {

  private static final String FIELD_NAME = "Firstname Secondname";
  private static final int FIELD_AGE = 30;
  private static final String DEFAULT_USER_LOGIN = "user1";
  private static final String DEFAULT_USER_PASSWORD = "11111";


  public User buildDefaultUser() {
    User user = new User.UserBuilder()
        .withName(FIELD_NAME)
        .withAge(FIELD_AGE)
        .withLogin(DEFAULT_USER_LOGIN)
        .withPassword(DEFAULT_USER_PASSWORD)
        .build();

    return user;
  }

  public User buildAnotherUser() {
    User user = new User.UserBuilder()
        .withName("Another Name")
        .withAge(25)
        .withLogin("user3")
        .withPassword("11111")
        .build();
    return user;
  }
}
