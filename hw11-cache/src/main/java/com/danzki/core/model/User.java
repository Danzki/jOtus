package com.danzki.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 03.02.19.
 * modified on 24.03.2020 by danzki
 */
@Entity
@Table(name = "User")
public class User {
  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @Column(name = "name")
  private String name;
  @Column(name = "age")
  private int age;
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private AddressDataSet addressDataSet;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PhoneDataSet> phones = new ArrayList<>();


  public User() {
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

  public User(long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public static class UserBuilder {
    private long id;
    private String name;
    private int age;

    public UserBuilder() {
    }

    public UserBuilder withId(long id) {
      this.id = id;
      return this;
    }

    public UserBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public UserBuilder withAge(int age) {
      this.age = age;
      return this;
    }

    public User build() {
      return new User(id, name, age);
    }
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age='" + age + '\'' +
        '}';
  }
}
