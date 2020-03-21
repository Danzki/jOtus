package com.danzki.core.model;

import com.danzki.core.annotations.Id;

/**
 * @author sergey
 * created on 03.02.19.
 * modified on 12.03.2020 by danzki
 */
public class User {
  @Id
  private long id;
  private String name;
  private int age;

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
