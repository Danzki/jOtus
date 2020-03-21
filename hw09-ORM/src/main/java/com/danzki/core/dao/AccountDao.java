package com.danzki.core.dao;

import com.danzki.core.model.Account;
import com.danzki.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  SessionManager getSessionManager();
  long create(Account account);
  void update(Account account);
  Optional<Account> load(long id, Class<Account> clazz);
}
