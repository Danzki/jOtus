package com.danzki.depart.classes;

import com.danzki.atm.AtmVisitor;
import com.danzki.atm.CellService;
import com.danzki.atm.classes.Atm;
import com.danzki.atm.classes.Cell;

public class BalanceVisitor implements AtmVisitor, CellService {
  @Override
  public Integer giveStatement(Atm atm) {
    return null;
  }

  @Override
  public Integer getStatement(Cell cell) {
    return null;
  }

}
