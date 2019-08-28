package com.danzki;

import com.danzki.atm.classes.Atm;
import com.danzki.depart.Departable;
import com.danzki.depart.classes.Depart;

public class DepartApplication {
  public static void initialization(Departable depart) {
    for(int i=0; i < 5; i++) {
      var atm = new Atm(i);
      atm.loadAtm();
      atm.setConfig("DefaultConfig"+i);
      depart.addAtm(atm);
      depart.saveAtm(atm);
    }
  }

  public static void main(String[] args) {
    var depart = new Depart();
    initialization(depart);

    depart.getAtmList().stream()
        .forEach(atm -> depart.configureAtm(atm,
            depart.getAtmConfig(atm).
                replace("Default", "New")));

    System.out.println("ATMs configured to New config:");
    depart.getAtmList().stream()
        .map(atm -> depart.getAtmConfig(atm))
        .forEach(System.out::println);

    depart.getAtmList().stream()
        .forEach(atm -> depart.restoreAtm(atm));

    System.out.println("ATM's configs are restored:");
    depart.getAtmList().stream()
        .map(atm -> depart.getAtmConfig(atm))
        .forEach(System.out::println);

    System.out.println("ATM's statement:");
    depart.getAtmList().stream()
        .map(atm -> depart.getAtmStatement(atm))
        .forEach(System.out::println);
  }
}
