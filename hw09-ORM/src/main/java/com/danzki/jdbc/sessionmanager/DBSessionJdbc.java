package com.danzki.jdbc.sessionmanager;

import com.danzki.api.sessionmanager.DBSession;

import java.sql.Connection;

public class DBSessionJdbc implements DBSession {
  private final Connection connection;
  DBSessionJdbc(Connection connection) {
    this.connection = connection;
  }
  public Connection getConnection() {
    return connection;
  }
}
