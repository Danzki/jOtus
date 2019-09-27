package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.SimpleJson;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class SimpleJsonWriter implements SimpleJson {
  private JsonObject jsonObject;
  private JsonArray jsonArray;

  private List<JsonObjectBuilder> jsonBuilders;

  public SimpleJsonWriter() {
    jsonBuilders = new ArrayList<>();
  }

  @Override
  public String toJson(String string) {
    if (isNull(string)) {
      return JsonValue.NULL.toString();
    }
    return Json.createValue(string).toString();
  }

  @Override
  public String toJson(boolean bool) {
    return bool ? JsonValue.TRUE.toString() : JsonValue.FALSE.toString();
  }

  @Override
  public String toJson(int number) {
    return Json.createValue(number).toString();
  }

  @Override
  public String toJson(long number) {
    return Json.createValue(number).toString();
  }

  @Override
  public String toJson(double number) {
    return Json.createValue(number).toString();
  }

  @Override
  public String toJson(float number) {
    return Json.createValue(number).toString();
  }

  @Override
  public String toJson(short number) {
    return Json.createValue(number).toString();
  }

  @Override
  public String toJson(byte number) {
    return Json.createValue(number).toString();
  }

  @Override
  public String toJson(char c) {
    return Json.createValue(Character.toString(c)).toString();
  }

  private boolean isNull(Object object) {
    return object == null ? true : false;
  }

  @Override
  public String toJson(Object object) throws IllegalAccessException {
    if (object.getClass().isArray()) {
      jsonArray = navigateArray(object);
      return jsonArray.toString();
    } else if (object instanceof Collection) {
      jsonArray = navigateArray(((Collection) object).toArray());
      return jsonArray.toString();
    } else {
      jsonObject = navigateObject(object);
      return jsonObject.toString();
    }
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
        continue;
      } else if (field.getType().isPrimitive() || field.getType().isAssignableFrom(Boolean.class)) {
        visitPrimitive(field, fieldObject);
      } else if (field.getType().isAssignableFrom(String.class)) {
        visitString(field, fieldObject);
      } else if (field.getType().isAssignableFrom(List.class)) {
        visitFieldArray(field.getName(), ((Collection) fieldObject).toArray());
      } else if (field.getType().isArray()) {
        visitFieldArray(field.getName(), fieldObject);
      } else {
        navigateObject(fieldObject);
      }
    }
    return jsonBuild();
  }

  private JsonArray navigateArray(Object object) {
    JsonArrayBuilder jsonArrayBuilder = getJsonArrayBuilder(object);
    return jsonArrayBuilder.build();
  }

  public JsonObject jsonBuild() {
    var jsonBuilder = Json.createObjectBuilder();
    for (JsonObjectBuilder jb : jsonBuilders) {
      jsonBuilder.addAll(jb);
    }
    jsonObject = jsonBuilder.build();

    return jsonObject;
  }

  private void visitPrimitive(Field fieldVisited, Object objectVisited) {
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

  private void visitFieldArray(String name, Object objectVisited) {
    JsonArrayBuilder jsonArrayBuilder = getJsonArrayBuilder(objectVisited);

    if (name != null) {
      var jsonBuilder = Json.createObjectBuilder()
          .add(name, jsonArrayBuilder);
      jsonBuilders.add(jsonBuilder);
    }
  }

  private JsonArrayBuilder getJsonArrayBuilder(Object objectVisited) {
    Object arrayObj = objectVisited;
    int length = Array.getLength(arrayObj);
    var jsonArrayBuilder = Json.createArrayBuilder();
    for (int i = 0; i < length; i++) {
      Object value = Array.get(arrayObj, i);
      if (value instanceof String) {
        jsonArrayBuilder.add(String.valueOf(value));
      } else if (value instanceof Integer) {
        jsonArrayBuilder.add((Integer) value);
      } else if (value instanceof Boolean) {
        jsonArrayBuilder.add((Boolean) value);
      }
    }
    return jsonArrayBuilder;
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
