package com.danzki.jdbc.sessionmanager;

import com.danzki.api.sessionmanager.DBSession;
import com.danzki.api.sessionmanager.SessionManager;
import com.danzki.api.sessionmanager.SessionManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class SessionManagerJdbc implements SessionManager {
  private static final int TIMEOUT_IN_SECONDS = 5;
  private final DataSource dataSource;
  private DBSessionJdbc databaseSession;
  private Connection connection;

  public SessionManagerJdbc(DataSource dataSource) {
    if (dataSource == null) {
      throw new SessionManagerException("Datasource is null");
    }
    this.dataSource = dataSource;
  }

  @Override
  public void open() {
    try {
      connection = dataSource.getConnection();
      databaseSession = new DBSessionJdbc(connection);
    } catch (SQLException e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void close() throws SessionManagerException {
    checkConnection();
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void rollback() {
    checkConnection();
    try {
      connection.rollback();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void rollback(Savepoint savepoint) {
    checkConnection();
    try {
      connection.rollback(savepoint);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void commit() {
    checkConnection();
    try {
      connection.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public DBSession getCurrentSession() {
    checkConnection();
    return databaseSession;
  }

  private void checkConnection() {
    try {
      if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
        throw new SessionManagerException("Connection is invalid");
      }
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }
}
