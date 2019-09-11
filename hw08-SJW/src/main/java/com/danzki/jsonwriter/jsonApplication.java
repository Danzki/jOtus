package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.JsonService;
import com.danzki.jsonwriter.classes.ObjectTracker;
import com.danzki.jsonwriter.classes.examples.Employee;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class jsonApplication {
  public static void main(String[] args) {
    Employee emp1 = Employee
        .builder()
        .isWork(true)
        .firstName("Daniil")
        .lastName("Kapustin")
        .age(35)
        .params(new String[]{"p0", "p1", "p2"})
        .phones(new ArrayList<>(List.of("2222", "4444", "6666")))
        .build();
    var objectTracker = new ObjectTracker();
    var jsonCreated = objectTracker.trackMethod(null, emp1, new JsonService());

    StringWriter stWriter = new StringWriter();
    try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
      jsonWriter.writeObject(jsonCreated);
    }

    System.out.println(stWriter);
  }
}
