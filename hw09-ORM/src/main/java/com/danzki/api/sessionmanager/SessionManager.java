package com.danzki.api.sessionmanager;

public interface SessionManager extends AutoCloseable {
  void open();
  void close();
  void rollback();
  void commit();

  DBSession getCurrentSession();
}
