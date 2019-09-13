package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.JsonService;
import com.danzki.jsonwriter.classes.ObjectTracker;
import com.danzki.jsonwriter.examples.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Simplet JSON writer tests:")
public class jsonApplicationTest {
  Employee testEmployee;
  ObjectTracker testTracker;
  JsonObject jsonCreated;

  @BeforeEach
  public void initiate() throws IOException, IllegalAccessException {
    testEmployee = Employee
        .builder()
        .isWork(true)
        .firstName("Daniil")
        .lastName("Kapustin")
        .age(35)
        .params(new String[]{"p0", "p1", "p2"})
        .phones(new ArrayList<>(List.of("2222", "4444", "6666")))
        .build();

    testTracker = new ObjectTracker();
  }

  @DisplayName("Create JSON and Desializate it with GSON")
  @Test
  public void jsonImportWithGsonTest() throws IllegalAccessException {
    jsonCreated = testTracker.trackMethod(null, testEmployee, new JsonService());
    String expected = testTracker.getJsonString(jsonCreated);


    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.serializeNulls();
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    StringWriter actualWriter = new StringWriter();
    gson.toJson(testEmployee, actualWriter);

    assertEquals(expected.replaceAll("\\s+", ""), actualWriter.toString().replaceAll("\\s+", ""));
  }
}
