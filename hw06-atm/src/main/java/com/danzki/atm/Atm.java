package com.danzki.atm;


import com.danzki.atm.exceptions.IncorrectAmount;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Atm {
  private TreeMap<Banknote, Cell> cells;

  public TreeMap<Banknote, Cell> getCells() {
    return cells;
  }

  public Map<Integer, Integer> giveCash(int requestedAmount) throws IncorrectAmount {
    var givenBanknotes = new HashMap<Integer, Integer>();
    int amount = requestedAmount;
    if (amount % getMinimalNominal() != 0 || amount > getCurrentStatement()) {
      throw new IncorrectAmount("Amount cannot be issued.");
    }
    for (Map.Entry<Banknote, Cell> cell : cells.entrySet()) {
      int nominal = cell.getKey().getNominal();
      if (nominal <= requestedAmount) {
        int banknotesCount = cell.getValue().getBanknotesCount(amount, nominal);
        if (banknotesCount > 0) {
          amount = amount % nominal;
          givenBanknotes.put(nominal, banknotesCount);
          if (amount == 0) {
            break;
          }
        }
      }
    }
    return givenBanknotes;
  }

  private int getMinimalNominal() {
    return cells.lastKey().getNominal();
  }

  public void loadAtm() {
    cells = new TreeMap<>(Banknote.nominalComparator);
    fillCell(Banknote.HUNDRED, 2500);
    fillCell(Banknote.TWOHUNDRED, 2500);
    fillCell(Banknote.FIVEHUNDRED, 2500);
    fillCell(Banknote.THOUSAND, 2500);
    fillCell(Banknote.TWOTHOUSAND, 2500);
    fillCell(Banknote.FIVETHOUSAND, 2500);
  }

  private void fillCell(Banknote banknote, int size) {
    cells.put(banknote, new Cell(size));
  }

  public int getCurrentStatement() {
    int statement = 0;
    for (Map.Entry<Banknote, Cell> cell : cells.entrySet()) {
      statement += cell.getValue().getStatement(cell.getKey().getNominal());
    }

    return statement;
  }
}
