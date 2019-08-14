package com.danzki.atm;

public class Cell {
  private int size;

  public Cell(int size) {
    this.size = size;
  }

  public int getStatement(int nominal) {
    return nominal * size;
  }

  public int getBanknotesCount(int requestedAmount, int nominal) {
    int givenCount = requestedAmount / nominal;
    if (giveBanknotes(givenCount)) {
      return givenCount;
    }
    return 0;
  }

  public boolean giveBanknotes(int requestedCount) {
    if (size < requestedCount) {
      return false;
    }
    size -= requestedCount;
    return true;
  }

}
