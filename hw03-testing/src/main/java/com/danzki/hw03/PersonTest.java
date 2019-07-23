package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.After;
import com.danzki.hw03.TestingEnvironment.Before;
import com.danzki.hw03.TestingEnvironment.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {

  @Before
  static void beforeTest() {
    System.out.println("Some text before every test.");
  }

  @Test
  static void testFirstName() {
    assertThat(new Person("Dan", "Kapustin", 35).getFirstName())
            .contains("Dan");
  }

  @Test
  static void testLastName() {
    assertThat(new Person("Dan", "Kapustin", 35).getLastName())
        .contains("Kapustin");
  }

  @After
  static void afterTest() {
    System.out.println("Some text after every test.");
  }

}
