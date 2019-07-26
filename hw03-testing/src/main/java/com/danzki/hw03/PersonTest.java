package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.annotations.After;
import com.danzki.hw03.TestingEnvironment.annotations.Before;
import com.danzki.hw03.TestingEnvironment.annotations.Test;
import static org.assertj.core.api.Assertions.*;

public class PersonTest {
  private static Person person = new Person("Dan", "Kapustin", 35);

  @Before
  static void beforeTest() {
    System.out.println("Methods BEFORE every test.");
  }

  @Test
  static void testFirstName() {
    String expectedValue = "Dan";
    assertThat(person.getFirstName())
        .withFailMessage("Wrong Last name. " +
            "Expected - " + expectedValue + ", given - " + person.getFirstName())
        .contains(expectedValue);
  }

  @Test
  static void testLastName() {
    String expectedValue = "Petrov";
    assertThat(person.getLastName())
        .withFailMessage("Wrong Last name. " +
            "Expected - " + expectedValue + ", given - " + person.getLastName())
        .contains(expectedValue);
  }

  @Test
  static void testFullName() {
    String expectedValue = "Dan Kapustin";
    assertThat(person.getFullName())
        .withFailMessage("Wrong Last name. " +
            "Expected - " + expectedValue + ", given - " + person.getFullName())
        .contains(expectedValue);
  }

  @After
  static void afterTest() {
    System.out.println("Methods AFTER every test.");
  }

}
