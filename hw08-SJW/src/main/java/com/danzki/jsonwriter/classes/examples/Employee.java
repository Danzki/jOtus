package com.danzki.jsonwriter.classes.examples;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
  String firstName;
  String lastName;
  int age;
  int castaId;
}
