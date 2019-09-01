package com.danzki.atm;

public interface Cellable {
  int getStatement(int nominal);
  boolean giveBanknotes(int requestedCount);
  int getBanknotesCount(int requestedAmount, int nominal);
  void loadCell();
  Integer acceptService(CellService service);
}
