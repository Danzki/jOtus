package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.examples.Employee;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.StringWriter;

public class jsonApplication {
  public static void main(String[] args) {
    Employee emp1 = Employee
        .builder()
        .isWork(true)
        .firstName("Daniil")
        .lastName("Kapustin")
        .age(35)
        .castaId(123)
        .params(new String[]{"p1", "p2"})
        .build();
//    var objectTracker = new ObjectTracker();
//    objectTracker.trackMethod(null, emp1, new JsonService());
    var jsonCreated = new EmpToJson(emp1).create();
    StringWriter stWriter = new StringWriter();
    try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
      jsonWriter.writeObject(jsonCreated);
    }

    System.out.println(stWriter);
  }
}
