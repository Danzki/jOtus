package com.danzki.atm;

import com.danzki.atm.exceptions.AtmException;

import java.util.Map;

public interface Atmable {
  Map<Integer, Integer> giveCash(int requestedAmount) throws AtmException;
  int getCurrentStatement();
}
