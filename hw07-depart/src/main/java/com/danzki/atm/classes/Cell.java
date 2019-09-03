package com.danzki.atm.classes;

import com.danzki.atm.CellService;
import com.danzki.atm.Cellable;

public class Cell implements Cellable {
  private int size;
  private int nominal;

  public Cell(int nominal, int size) {
    this.size = size;
    this.nominal = nominal;
  }

  public int getSize() {
    return size;
  }

  public int getNominal() {
    return nominal;
  }

  @Override
  public int getStatement(int nominal) {
    if (this.nominal == nominal) {
      return nominal * size;
    }
    else {
      return 0;
    }
  }

  @Override
  public int getBanknotesCount(int requestedAmount, int nominal) {
    if (this.nominal == nominal) {
      int givenCount = requestedAmount / nominal;
      if (giveBanknotes(givenCount)) {
        return givenCount;
      }
    }
    return 0;
  }

  @Override
  public void loadCell() {

  }

  @Override
  public boolean giveBanknotes(int requestedCount) {
    if (size < requestedCount) {
      return false;
    }
    size -= requestedCount;
    return true;
  }

  @Override
  public Integer acceptService(CellService service) {
    return service.getStatement(this);
  }

}
