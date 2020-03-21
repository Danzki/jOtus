package com.danzki.jdbc.sessionmanager;

import com.danzki.core.sessionmanager.DatabaseSession;

import java.sql.Connection;

public class DatabaseSessionJdbc implements DatabaseSession {
  private final Connection connection;

  DatabaseSessionJdbc(Connection connection) {
    this.connection = connection;
  }

  public Connection getConnection() {
    return connection;
  }
}
