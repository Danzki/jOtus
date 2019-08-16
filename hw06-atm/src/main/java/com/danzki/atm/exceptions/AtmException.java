package com.danzki.atm.exceptions;

public class AtmException extends Exception{
  public AtmException(Throwable cause, String s) {
    super(s, cause);
  }
}
