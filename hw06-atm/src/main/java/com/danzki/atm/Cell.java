package com.danzki.atm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class Cell {
  final int maxSize = 2500;
  Integer nominal;
  int size;

  public int getNominal() {
    return nominal;
  }

  public boolean giveBanknotes(int banknotesCount) {
    if (size < banknotesCount) {
      return false;
    }
    size -= banknotesCount;
    return true;
  }

}
