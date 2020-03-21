package com.danzki.core.service;


import com.danzki.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

  long saveAccount(Account account);
  void update(Account account);
  Optional<Account> getAccount(long id);

}
