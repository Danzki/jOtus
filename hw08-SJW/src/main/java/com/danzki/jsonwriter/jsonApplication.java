package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.SimpleJsonWriter;
import com.danzki.jsonwriter.examples.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class jsonApplication {
  public static void main(String[] args) throws IllegalAccessException, IOException {
    var emp1 = createEmployee();
    var sJson = new SimpleJsonWriter().toJson(emp1);
    SimpleJsonWriter.writeToFile(sJson, emp1.getClass().getSimpleName());
    Gson gson = gsonWriter(emp1, emp1.getClass().getSimpleName());
  }

  private static Gson gsonWriter(Employee emp1, String fileName) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.serializeNulls();
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    try (FileWriter writer = new FileWriter("./out/" + fileName + "_GSON.json")) {
      gson.toJson(emp1, writer);
      System.out.println("File " + fileName + "_GSON.json is created");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return gson;
  }

  private static Employee createEmployee() {
    Employee emp1 = Employee
        .builder()
        .isWork(true)
        .firstName("Daniil")
        .lastName("Kapustin")
        .age(35)
        .params(new String[]{"p0", "p1", "p2"})
        .phones(new ArrayList<>(List.of("2222", "4444", "6666")))
        .build();
    return emp1;
  }
}
