package com.danzki.core.model;

import com.danzki.core.annotations.Id;

public class Account {
  /*
  no bigint(20) NOT NULL auto_increment
  type varchar(255)
  rest number
   */

  @Id
  long no;
  String type;
  int rest;

  public Account(long no, String type, int rest) {
    this.no = no;
    this.type = type;
    this.rest = rest;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setRest(int rest) {
    this.rest = rest;
  }

  public String getType() {
    return type;
  }

  public int getRest() {
    return rest;
  }

  @Override
  public String toString() {
    return "Account{" +
        "no=" + no +
        ", type='" + type + '\'' +
        ", rest=" + rest +
        '}';
  }

  public static class AccountBuilder {
    private long no;
    private String type;
    private int rest;

    public AccountBuilder() {
    }

    public AccountBuilder withNo(long no) {
      this.no = no;
      return this;
    }

    public AccountBuilder withType(String type) {
      this.type = type;
      return this;
    }

    public AccountBuilder withRest(int rest) {
      this.rest = rest;
      return this;
    }

    public Account build() {
      return new Account(no, type, rest);
    }
  }
}
