package com.danzki;

import com.danzki.h2.DataSourceH2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DBServiceApp {
  private static Logger logger = LoggerFactory.getLogger(DBServiceApp.class);

  public static void main(String[] args) {
    DataSource dataSource = new DataSourceH2();


  }
}
