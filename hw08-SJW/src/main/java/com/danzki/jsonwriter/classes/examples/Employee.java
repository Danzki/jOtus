package com.danzki.jsonwriter.classes.examples;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Employee {
  String firstName;
  String lastName;
  String secondName;
  int age;
  int castaId;
  boolean isWork;
  private String[] params;
  List<String> phones;
}
