package com.danzki.atm;


import com.danzki.atm.exceptions.IncorrectAmount;
import com.danzki.atm.exceptions.NotEnoughCash;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

@Getter
public class Atm {
  @Setter
  List<Cell> cells;

  @SneakyThrows
  public Map<Integer, Integer> giveCash(int requestedAmount) {
    int amount = requestedAmount;
    Integer minNominal = getMinimalNominal();
    if (amount % minNominal != 0) {
      throw new IncorrectAmount("Amount cannot be issued. Please enter amount multiple to " + minNominal);
    }
    if (amount > getCurrentStatement()) {
      throw new NotEnoughCash("Amount cannot be issued. Not enough cash.");
    }

    Map<Integer, Integer> returnBanknotes = new HashMap<>();
    List<Integer> nominals = getNominals();
    Collections.reverse(nominals);
    for (Integer nominal : nominals) {
      if (amount >= nominal) {
        returnBanknotes.put(nominal, amount / nominal);
        amount = amount % nominal;
      }
      if (amount <= 0) {
        break;
      }
    }

    return returnBanknotes;
  }
  
  private List<Integer> getNominals() {
    List<Integer>  nominals = new ArrayList<>();
    for (Cell cell : cells) {
      nominals.add(cell.getNominal());
    }
    return nominals;
  }

  private Integer getMinimalNominal() {
    List<Integer> nominals = getNominals();
    return Collections.min(nominals);
  }

  public int getCurrentStatement() {
    int statement = 0;
    for (Cell cell : cells) {
      statement += cell.getNominal() * cell.getSize();
    }

    return statement;
  }

  public void loadCells(int[] nominals, int banknoteCount) {
    cells = new ArrayList<>();
    for (int i = 0; i < nominals.length; i++) {
      var cell = new Cell();
      cell.setNominal(nominals[i]);
      cell.setSize(banknoteCount);
      cells.add(cell);
    }
  }

}
