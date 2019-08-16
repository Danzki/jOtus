package com.danzki.atm;

import com.danzki.atm.exceptions.IncorrectAmount;
import com.danzki.atm.exceptions.NotEnoughCash;

import java.util.Map;

public interface Atmable {
  Map<Integer, Integer> giveCash(int requestedAmount) throws NotEnoughCash, IncorrectAmount;
  int getCurrentStatement();
}
