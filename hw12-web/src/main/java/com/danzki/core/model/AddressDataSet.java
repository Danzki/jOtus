package com.danzki.core.model;

public class AddressDataSet {
  private long id;
  private String street;
  private User user;

  public AddressDataSet() {
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public AddressDataSet(String street, User user) {
    this.street = street;
    this.user = user;
  }
}
