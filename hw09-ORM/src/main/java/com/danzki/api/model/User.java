package com.danzki.api.model;

public class User {
  @Id
  long id;
  String name;
  int age;

  public User(long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return "User{" +
        "no=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}
