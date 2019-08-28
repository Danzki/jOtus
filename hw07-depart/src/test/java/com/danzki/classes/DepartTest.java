package com.danzki.classes;

import com.danzki.atm.Atmable;
import com.danzki.atm.classes.Atm;
import com.danzki.depart.classes.Depart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Depart class tests:")
public class DepartTest {
  private Depart depart;

  private Atm getAtmForTest() {
    var atm = new Atm(1);
    atm.setConfig("DefaultConfig1");
    atm.loadAtm();
    return atm;
  }

  @BeforeEach
  void init() {
    var atm = getAtmForTest();
    depart = new Depart();
    depart.addAtm(atm);
  }

  @Test
  @DisplayName("addAtm successful test")
  public void addAtmTest() {
    assertEquals(1, depart.getAtmList().size());
  }

  @Test
  @DisplayName("configureAtm test successful")
  public void configureTest() {
    var atm = depart.getAtmList().get(0);
    depart.configureAtm(atm, "NewConfig1");

    assertEquals("NewConfig1", ((Atm) atm).getConfig());
  }

  @Test
  @DisplayName("saveAtm&restore methods successful test")
  public void saveAtmSuccessfulTest() {
    for (Atmable atm : depart.getAtmList()) {
      depart.saveAtm(atm);
      depart.configureAtm(atm, "NewConfig1");
      depart.restoreAtm(atm);
    }
    assertEquals("DefaultConfig1", ((Atm) depart.getAtmList().get(0)).getConfig());
  }

  @Test
  @DisplayName("getStatement successful test")
  public void getStatementTest() {
    assertEquals(java.util.Optional.ofNullable(88000),
        java.util.Optional.ofNullable(depart.getAtmStatement(depart.getAtmList().get(0))));
  }
}
