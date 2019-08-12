package com.danzki.atm.interfaces;

public interface CellObservable{
  void addCell(AtmRequest request);
  void removeCell(AtmRequest request);
  void notifyCell(AtmRequest request);
}
