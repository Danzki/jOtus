package com.danzki.depart.classes;

import com.danzki.atm.AtmVisitor;
import com.danzki.atm.Cellable;
import com.danzki.atm.classes.Atm;
import com.danzki.atm.classes.CellServicer;

public class AtmDoVisitor implements AtmVisitor {
  @Override
  public Integer giveStatement(Atm atm) {
    Integer statement = 0;
    for (Cellable cell : atm.getCells().values()) {
      statement += cell.acceptService(new CellServicer());
    }
    return statement;
  }
}
