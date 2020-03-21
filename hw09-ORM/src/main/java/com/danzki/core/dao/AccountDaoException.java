package com.danzki.core.dao;

public class AccountDaoException extends RuntimeException {
  public AccountDaoException(Exception ex) {
    super(ex);
  }
}
