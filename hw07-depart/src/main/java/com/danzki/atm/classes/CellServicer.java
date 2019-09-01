package com.danzki.atm.classes;

import com.danzki.atm.CellService;

public class CellServicer implements CellService {
  @Override
  public Integer getStatement(Cell cell) {
    return cell.getStatement(cell.getNominal());
  }
}
