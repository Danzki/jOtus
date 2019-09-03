package com.danzki.atm.classes;


import com.danzki.atm.Atmable;
import com.danzki.atm.Cellable;
import com.danzki.atm.exceptions.AtmException;
import com.danzki.atm.exceptions.IncorrectAmount;
import com.danzki.atm.exceptions.NotEnoughCash;
import com.danzki.depart.classes.AtmConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Atm implements Atmable {
  @Getter
  private TreeMap<Banknote, Cellable> cells;
  @Getter
  @Setter
  private String config;

  @Getter
  private final Integer atmId;

  public Atm(Integer atmId) {
    this.atmId = atmId;
  }

  @Override
  public AtmConfig saveConfig() {
    return new AtmConfig(atmId, config);
  }

  @Override
  public void restoreConfig(AtmConfig atmConfig) {
    config = atmConfig.getConfig();
  }

  @Override
  public Map<Integer, Integer> giveCash(int requestedAmount) throws AtmException {
    var givenBanknotes = new HashMap<Integer, Integer>();
    int amount = requestedAmount;
    int minimalNominal = getMinimalNominal();
    if (amount > getCurrentStatement()) {
      throw new AtmException(new NotEnoughCash("Amount cannot be issue"));
    }
    if (amount % minimalNominal != 0) {
      throw new AtmException(new IncorrectAmount("Amount should be multiple of " + minimalNominal));
    }
    for (Map.Entry<Banknote, Cellable> cell : cells.entrySet()) {
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
    cells.put(banknote, new Cell(banknote.getNominal(), size));
  }

  @Override
  public int getCurrentStatement() {
    int statement = 0;
    for (Map.Entry<Banknote, Cellable> cell : cells.entrySet()) {
      var command = new StatementCommand(cell.getValue(), cell.getKey().getNominal());
      statement += command.execute();
    }

    return statement;
  }
}
