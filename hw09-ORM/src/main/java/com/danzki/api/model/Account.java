package com.danzki.api.model;

public class Account {
  @Id
  long no;
  String type;
  long rest;

  public Account(long id, String type, long rest) {
    this.no = id;
    this.type = type;
    this.rest = rest;
  }

  public String getType() {
    return type;
  }

  public long getRest() {
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
}
