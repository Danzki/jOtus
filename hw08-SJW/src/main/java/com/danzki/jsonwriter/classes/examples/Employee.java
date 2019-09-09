package com.danzki.jsonwriter.classes.examples;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Employee {
  boolean isWork;
  String firstName;
  String lastName;
  int age;
  int castaId;
  private String[] params;
}
