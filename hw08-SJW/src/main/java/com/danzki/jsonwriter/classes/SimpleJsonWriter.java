package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.SimpleJson;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class SimpleJsonWriter implements SimpleJson {
  private JsonObject jsonObject;

  private List<JsonObjectBuilder> jsonBuilders;

  public SimpleJsonWriter() {
    jsonBuilders = new ArrayList<>();
  }

  @Override
  public JsonObject toJson(Object object) throws IllegalAccessException {
    jsonObject = navigateObject(object);
    return jsonObject;
  }

  private JsonObject navigateObject(Object object) throws IllegalAccessException {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      Object fieldObject = field.get(object);
      if (fieldObject == null) {
        visitNull(field);
      } else if (field.getType().isPrimitive() || field.getType().isAssignableFrom(Boolean.class)) {
        visitPrimitive(field, fieldObject);
      } else if (field.getType().isAssignableFrom(String.class)) {
        visitString(field, fieldObject);
      } else if (field.getType().isAssignableFrom(List.class)) {
        visitArray(field, ((Collection) fieldObject).toArray());
      } else if (field.getType().isArray()) {
        visitArray(field, fieldObject);
      } else {
        navigateObject(fieldObject);
      }
    }
    return jsonBuild();
  }

  public JsonObject jsonBuild() {
    var jsonBuilder = Json.createObjectBuilder();
    for (JsonObjectBuilder jb : jsonBuilders) {
      jsonBuilder.addAll(jb);
    }
    jsonObject = jsonBuilder.build();

    return jsonObject;
  }

  private void visitNull(Field fieldVisited) {
    var jsonBuilder = Json.createObjectBuilder()
        .add(fieldVisited.getName(), JsonValue.NULL);
    jsonBuilders.add(jsonBuilder);
  }

  private void visitPrimitive(Field fieldVisited, Object objectVisited) throws IllegalAccessException {
    String name = fieldVisited.getType().getName();
    var jsonBuilder = Json.createObjectBuilder();
    if (name.equals("int") ||
        name.equals("long") ||
        name.equals("short") ||
        name.equals("byte")
    ) {
      jsonBuilder.add(fieldVisited.getName(), (Integer) objectVisited);
    } else if (name.equals("boolean") ||
        name.equals("java.lang.Boolean")) {
      jsonBuilder.add(fieldVisited.getName(),
          (Boolean) objectVisited ? JsonValue.TRUE : JsonValue.FALSE);
    }
    jsonBuilders.add(jsonBuilder);
  }

  private void visitString(Field fieldVisited, Object objectVisited) {
    var jsonBuilder = Json.createObjectBuilder()
        .add(fieldVisited.getName(),
            String.valueOf(objectVisited));
    jsonBuilders.add(jsonBuilder);
  }

  private void visitBoolean(Field fieldVisited, Object objectVisited) {
    var jsonBuilder = Json.createObjectBuilder()
        .add(fieldVisited.getName(),
            (Boolean) objectVisited ? JsonValue.TRUE : JsonValue.FALSE);
    jsonBuilders.add(jsonBuilder);
  }

  private void visitArray(Field fieldVisited, Object objectVisited) {
    Object arrayObj = objectVisited;
    int length = Array.getLength(arrayObj);
    var jsonArrayBuilder = Json.createArrayBuilder();
    for (int i = 0; i < length; i++) {
      Object value = Array.get(arrayObj, i);
      jsonArrayBuilder.add(String.valueOf(value));
    }

    var jsonBuilder = Json.createObjectBuilder()
        .add(fieldVisited.getName(), jsonArrayBuilder);
    jsonBuilders.add(jsonBuilder);
  }

  public static void writeToFile(JsonObject json, String fileName) throws IOException {
    FileWriter fWriter = new FileWriter("./out/" + fileName + ".json");
    Map<String, Boolean> config = buildConfig(JsonGenerator.PRETTY_PRINTING);
    JsonWriterFactory writerFactory = Json.createWriterFactory(config);
    try (JsonWriter jsonWriter = writerFactory.createWriter(fWriter)) {
      jsonWriter.writeObject(json);
      System.out.println("File " + fileName + ".json is created");
    }
  }

  private static Map<String, Boolean> buildConfig(String... options) {
    Map<String, Boolean> config = new HashMap<String, Boolean>();

    if (options != null) {
      for (String option : options) {
        config.put(option, true);
      }
    }
    return config;
  }
}
