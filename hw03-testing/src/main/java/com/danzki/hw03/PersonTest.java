package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.annotations.After;
import com.danzki.hw03.TestingEnvironment.annotations.Before;
import com.danzki.hw03.TestingEnvironment.annotations.Test;
import static org.assertj.core.api.Assertions.*;

public class PersonTest {

  @Before
  static void beforeTest() {
    System.out.println("Methods BEFORE every test.");
  }

  @Test
  static void testFirstName() {
    assertThat(new Person("Dan", "Kapustin", 35).getFirstName())
            .contains("Dan");
  }

  @Test
  static void testLastName() {
    assertThat(new Person("Dan", "Kapustin", 35).getLastName())
        .contains("Petrov");
  }

  @Test
  static void testFullName() {
    assertThat(new Person("Dan", "Kapustin", 35).getLastName())
        .contains("Kapustin");
  }

  @After
  static void afterTest() {
    System.out.println("Methods AFTER every test.");
  }

}
