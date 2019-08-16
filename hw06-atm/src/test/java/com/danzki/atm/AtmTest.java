package com.danzki.atm;

import com.danzki.atm.classes.Atm;
import com.danzki.atm.classes.Banknote;
import com.danzki.atm.classes.Cell;
import com.danzki.atm.exceptions.IncorrectAmount;
import com.danzki.atm.exceptions.NotEnoughCash;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ATM class tests:")
public class AtmTest {
  private Atm atm;

  private Integer getValueFromMap(Map<Integer, Integer> map, int key) {
    return map
        .entrySet()
        .stream()
        .filter(entry -> entry.getKey().equals(key))
        .findFirst()
        .get()
        .getValue();
  }

  @BeforeEach
  void init() {
    atm = new Atm();
    atm.loadAtm();
  }

  @Test
  @DisplayName("Current statement test.")
  void currentStatementTest() {
    assertEquals(88000, atm.getCurrentStatement());
  }

  @Test
  @DisplayName("Give Cash succesful test.")
  void giveCashSuccessTest() {
    try {
      Map<Integer, Integer> cash = atm.giveCash(1000);
      assertEquals((int) getValueFromMap(cash, 1000), 1);
    } catch (IncorrectAmount incorrectAmount) {
      incorrectAmount.printStackTrace();
    } catch (NotEnoughCash notEnoughCash) {
      notEnoughCash.printStackTrace();
    }
  }

  @Test
  @DisplayName("Get cell statement successful test")
  void getCellStatementSuccessTest() {
    assertEquals(new Cell(Banknote.HUNDRED.getNominal(), 10).getStatement(100), 1000);
  }

  @Test
  @DisplayName("Get cell statement wrong nominal test")
  void getCellStatementWrongNominalest() {
    assertEquals(new Cell(Banknote.TWOHUNDRED.getNominal(), 10).getStatement(500), 0);
  }

  @Test
  @DisplayName("Get banknotes count success test.")
  void getBanknotesCountSuccessTest() {
    assertEquals(new Cell(Banknote.HUNDRED.getNominal(), 10).
        getBanknotesCount(400, 100), 4);
  }

  @Test
  @DisplayName("Get banknotes count wrong nominal test.")
  void getBanknotesCountWrongNominalTest() {
    assertEquals(new Cell(Banknote.TWOHUNDRED.getNominal(), 10).
        getBanknotesCount(400, 500), 0);
  }

  @Test
  @DisplayName("Get banknotes count return 0 test.")
  void getBanknotesCountReturnZeroTest() {
    assertEquals(new Cell(Banknote.TWOHUNDRED.getNominal(), 10).
        getBanknotesCount(400, 100), 0);
  }

  @Test
  @DisplayName("Give cash thrown Not enough cash.")
  void giveCashNotEnoughTest() {
    assertThrows(NotEnoughCash.class, () -> atm.giveCash(100000));
  }

  @Test
  @DisplayName("Give cash thrown IncorrectAmount")
  void giveCashIncorrectAmountTest() {
    assertThrows(IncorrectAmount.class, () -> atm.giveCash(566));
  }

  @AfterAll
  static void afterTest() {
    System.out.println("All tests are completed.");
  }
}
