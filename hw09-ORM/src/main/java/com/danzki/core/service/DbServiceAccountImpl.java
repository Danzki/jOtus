package com.danzki.core.service;

import com.danzki.core.dao.AccountDao;
import com.danzki.core.model.Account;
import com.danzki.core.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
  private static Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

  private final AccountDao accountDao;

  public DbServiceAccountImpl(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public long saveAccount(Account account) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = accountDao.create(account);
        sessionManager.commitSession();

        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public void update(Account account) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        accountDao.update(account);
        sessionManager.commitSession();

        logger.info("updated user: {}", account.toString());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<Account> getAccount(long id) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<Account> accountOptional = accountDao.load(id, Account.class);

        logger.info("user: {}", accountOptional.orElse(null));
        return accountOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
