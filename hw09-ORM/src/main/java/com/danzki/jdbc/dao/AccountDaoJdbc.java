package com.danzki.jdbc.dao;


import com.danzki.core.dao.AccountDao;
import com.danzki.core.dao.UserDaoException;
import com.danzki.core.model.Account;
import com.danzki.core.sessionmanager.SessionManager;
import com.danzki.jdbc.DbExecutor;
import com.danzki.jdbc.Mapper;
import com.danzki.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
  private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<Account> dbExecutor;
  private final Mapper<Account> mapper;

  public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor, Mapper<Account> mapper) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
    this.mapper = mapper;
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  @Override
  public long create(Account account) {
    try {
      String query = mapper.getInsertQuery();
      List<String> params = new ArrayList<>();
      params.add(account.getType());
      params.add(String.valueOf(account.getRest()));
      return dbExecutor.insertRecord(getConnection(), query, params);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public void update(Account account) {
    try {
      String query = mapper.getUpdateQuery(account);
      dbExecutor.updateRecord(getConnection(), query, mapper.getPrimaryKeyId(account));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public Optional<Account> load(long id, Class<Account> clazz) {
    try {
      String query = mapper.getSelectQuery();
      return dbExecutor.selectRecord(getConnection(), query, id, resultSet -> {
        try {
          if (resultSet.next()) {
            return new Account(resultSet.getLong("no"), resultSet.getString("type"), resultSet.getInt("rest"));
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }


  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
