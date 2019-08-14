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

//  @SneakyThrows
  public Map<Integer, Integer> giveCash(int requestedAmount) throws IncorrectAmount {
    var givenBanknotes = new HashMap<Integer, Integer>();
    int amount = requestedAmount;
    int minimalNominal = getMinimalNominal();
    if (amount > getCurrentStatement()) {
      throw new IncorrectAmount("Amount cannot be issue");
    }
    if (amount % minimalNominal != 0) {
      throw new IncorrectAmount("Amount should be multiple of " + minimalNominal);
    }
    for (Map.Entry<Banknote, Cell> cell : cells.entrySet()) {
      int nominal = cell.getKey().getNominal();
      if (nominal <= amount) {
        var command = new GiveCommand(cell.getValue(), amount, nominal);
        int banknotesCount = command.execute();
        amount = amount % nominal;
        givenBanknotes.put(nominal, banknotesCount);
        if (amount == 0) {
          break;
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
    fillCell(Banknote.HUNDRED, 10);
    fillCell(Banknote.TWOHUNDRED, 10);
    fillCell(Banknote.FIVEHUNDRED, 10);
    fillCell(Banknote.THOUSAND, 10);
    fillCell(Banknote.TWOTHOUSAND, 10);
    fillCell(Banknote.FIVETHOUSAND, 10);
  }

  private void fillCell(Banknote banknote, int size) {
    cells.put(banknote, new Cell(size));
  }

  public int getCurrentStatement() {
    int statement = 0;
    for (Map.Entry<Banknote, Cell> cell : cells.entrySet()) {
      var command = new StatementCommand(cell.getValue(), cell.getKey().getNominal());
      statement += command.execute();
    }

    return statement;
  }
}
