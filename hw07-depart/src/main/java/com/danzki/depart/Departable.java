package com.danzki.depart;

import com.danzki.atm.Atmable;

public interface Departable {
  void addAtm(Atmable atm);
  Integer getAtmStatement(Atmable atm);
  void restoreAtm(Atmable atm);
  void saveAtm(Atmable atm);
}
