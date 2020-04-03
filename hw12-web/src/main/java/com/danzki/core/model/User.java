package com.danzki.core.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class User {
  private ObjectId _id;
  private String name;
  private int age;
  private AddressDataSet addressDataSet;
  private List<PhoneDataSet> phones = new ArrayList<>();
  private String login;
  private String password;

  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  public ObjectId get_id() {
    return _id;
  }

  public void setAddressDataSet(AddressDataSet addressDataSet) {
    this.addressDataSet = addressDataSet;
  }

  public void setPhones(List<PhoneDataSet> phones) {
    this.phones = phones;
  }

  public int getAge() {
    return age;
  }

  public User(String name, int age, String login, String password) {
    this.name = name;
    this.age = age;
    this.login = login;
    this.password = password;
  }

  public User() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public static class UserBuilder {
    private String name;
    private int age;
    private String login;
    private String password;

    public UserBuilder() {
    }

    public UserBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public UserBuilder withAge(int age) {
      this.age = age;
      return this;
    }

    public UserBuilder withLogin(String login) {
      this.login = login;
      return this;
    }

    public UserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public User build() {
      return new User(name, age, login, password);
    }
  }

  public String getName() {
    return name;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "User{" +
        "_id=" + _id.toString() +
        ", name='" + name + '\'' +
        ", age='" + age + '\'' +
        '}';
  }
}
