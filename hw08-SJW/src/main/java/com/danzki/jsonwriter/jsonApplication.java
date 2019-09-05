package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.JsonService;
import com.danzki.jsonwriter.classes.ObjectTracker;
import com.danzki.jsonwriter.classes.examples.Employee;

public class jsonApplication {
  public static void main(String[] args) {
    var emp1 = new Employee("Daniil", "Kapustin", 35, 123);
    var objectTracker = new ObjectTracker();
    objectTracker.trackMethod(null, emp1, new JsonService(0));
  }
}
