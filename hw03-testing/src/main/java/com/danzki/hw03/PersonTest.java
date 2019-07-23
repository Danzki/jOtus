package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.After;
import com.danzki.hw03.TestingEnvironment.Before;
import com.danzki.hw03.TestingEnvironment.Test;

public class PersonTest {

  @Before
  static void beforeTest() {
    System.out.println("Some text before every test.");
  }

  @Test
  static void testFirstName() {
//    assertThat(new Person("Dan", "Kapustin", 35).getFirstName())
//            .contains("Dan");
    Person person = new Person("Dan", "Kapustin", 35);

    if (!person.getFirstName().contains("Dan")) {
      throw new IllegalArgumentException();
    } else {
      System.out.println("Test FirstName is succesful.");
    }

  }

  @Test
  static void testLastName() {
//    assertThat(new Person("Dan", "Kapustin", 35).getLastName())
//        .contains("Kapustin");
    Person person = new Person("Dan", "Kapustin", 35);
    if (!person.getLastName().contains("Kapustin")) {
      throw new IllegalArgumentException();
    } else {
      System.out.println("Test LastName is succesful.");
    }

  }

  @Test
  static void testFullName() {
//    assertThat(new Person("Dan", "Kapustin", 35).getLastName())
//        .contains("Kapustin");
    Person person = new Person("Dan", "Kapustin", 35);
    if (!person.getFullName().contains("Dan Kapustin")) {
      throw new IllegalArgumentException();
    } else {
      System.out.println("Test FullName is succesful.");
    }

  }

  @After
  static void afterTest() {
    System.out.println("Some text after every test.");
  }

}
