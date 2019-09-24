package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.JsonService;
import com.danzki.jsonwriter.classes.ObjectTracker;
import com.danzki.jsonwriter.classes.SimpleJsonWriter;
import com.danzki.jsonwriter.examples.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Simplet JSON writer tests:")
public class jsonApplicationTest {
  Employee testEmployee;
  ObjectTracker testTracker;
  JsonObject jsonCreated;

  @BeforeEach
  public void initiate() {
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

  @DisplayName("Deserialization of SimpleJsonWriter returned JSON to Object")
  @Test
  public void getObjectFromSimpleJson() throws IllegalAccessException {
    var testJson = new SimpleJsonWriter().toJson(testEmployee);
    Employee gson = new Gson().fromJson(testJson.toString(), Employee.class);
  }

  @DisplayName("Several data types test.")
  @Test
  public void severalDataTypesTest() throws IllegalAccessException {
    Gson gson = new Gson();

    SimpleJsonWriter serializer = new SimpleJsonWriter();


    assertEquals(gson.toJson(null), serializer.toJson(null));
    assertEquals(gson.toJson((byte)1), serializer.toJson((byte)1));
    assertEquals(gson.toJson((short)1f), serializer.toJson((short)1f));
    assertEquals(gson.toJson(1), serializer.toJson(1));
    assertEquals(gson.toJson(1L), serializer.toJson(1L));
    assertEquals(gson.toJson(1f), serializer.toJson(1f));
    assertEquals(gson.toJson(1d), serializer.toJson(1d));
    assertEquals(gson.toJson("aaa"), serializer.toJson("aaa"));
    assertEquals(gson.toJson('a'), serializer.toJson('a'));
    assertEquals(gson.toJson(testEmployee), serializer.toJson(testEmployee));
    assertEquals(gson.toJson(new int[] {1, 2, 3}), serializer.toJson(new int[] {1, 2, 3}));
    assertEquals(gson.toJson(List.of(1, 2 ,3)), serializer.toJson(List.of(1, 2 ,3)));
    assertEquals(gson.toJson(Collections.singletonList(1)), serializer.toJson(Collections.singletonList(1)));
    assertEquals(gson.toJson(testEmployee), serializer.toJson(testEmployee));
  }

}
