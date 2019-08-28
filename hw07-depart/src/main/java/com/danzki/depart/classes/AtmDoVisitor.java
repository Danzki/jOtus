package com.danzki.depart.classes;

import com.danzki.atm.AtmVisitor;
import com.danzki.atm.classes.Atm;

public class AtmDoVisitor implements AtmVisitor {
  @Override
  public Integer giveStatement(Atm atm) {
    return atm.getCurrentStatement();
  }
}
