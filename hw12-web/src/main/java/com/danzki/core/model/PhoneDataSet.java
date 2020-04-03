package com.danzki.core.model;

public class PhoneDataSet {
  private long id;
  private String number;
  private User user;

  public PhoneDataSet() {
  }

  public PhoneDataSet(String number, User user) {
    this.number = number;
    this.user = user;
  }

  public static class PhoneDataSetBuilder {
    private long id;
    private String number;
    private User user;

    public PhoneDataSetBuilder() {
    }

    public PhoneDataSetBuilder withNumber(String number) {
      this.number = number;
      return this;
    }

    public PhoneDataSetBuilder withUser(User user) {
      this.user = user;
      return this;
    }

    public PhoneDataSet build() {
      return new PhoneDataSet(number, user);
    }
  }
}
