package com.danzki.atm;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ATM class tests:")
public class AtmTest {
  private Atm atm;

  @BeforeEach
  void init() {
    atm = new Atm();
    atm.loadAtm();
  }

  @Test
  @DisplayName("Current statement test.")
  void currentStatementTest() {
    assertEquals(62000, atm.getCurrentStatement());
  }

  @AfterAll
  static void afterTest() {
    System.out.println("All tests are completed.");
  }
}
