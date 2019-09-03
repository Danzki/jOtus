package com.danzki.atm;

import com.danzki.atm.classes.Atm;

public interface AtmVisitor {
  Integer giveStatement(Atm atm);
}
