package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTracker {

  public JsonObject trackMethod(Field mainfield, Object object, TrackService service) throws IllegalAccessException {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      Object fieldObject = field.get(object);
      if (fieldObject == null) {
        new TrackedNull(field).accept(service);
      } else if (field.getType().isPrimitive()) {
        new TrackedPrimitive(field, object).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new TrackedString(field, fieldObject).accept(service);
      } else if (field.getType().isAssignableFrom(Boolean.class)) {
        new TrackedBoolean(field, fieldObject).accept(service);
      } else if (field.getType().isAssignableFrom(List.class)) {
        new TrackedArray(field, ((Collection) fieldObject).toArray()).accept(service);
      } else if (field.getType().isArray()) {
        new TrackedArray(field, fieldObject).accept(service);
      } else {
        trackMethod(field, object, service);
      }
    }

    return service.jsonBuild();
  }

  public void writeToFile(JsonObject json, String fileName) throws IOException {
    FileWriter fWriter = new FileWriter("./out/" + fileName + ".json");
    Map<String, Boolean> config = buildConfig(JsonGenerator.PRETTY_PRINTING);
    JsonWriterFactory writerFactory = Json.createWriterFactory(config);
    try (JsonWriter jsonWriter = writerFactory.createWriter(fWriter)) {
      jsonWriter.writeObject(json);
      System.out.println("File " + fileName + ".json is created");
    }
  }

  public String getJsonString(JsonObject json) {
    StringWriter jsonString = new StringWriter();
    Map<String, Boolean> config = buildConfig(JsonGenerator.PRETTY_PRINTING);
    JsonWriterFactory writerFactory = Json.createWriterFactory(config);
    try (JsonWriter jsonWriter = writerFactory.createWriter(jsonString)) {
      jsonWriter.writeObject(json);
    }
    return jsonString.toString();
  }

  private Map<String, Boolean> buildConfig(String... options) {
    Map<String, Boolean> config = new HashMap<String, Boolean>();

    if (options != null) {
      for (String option : options) {
        config.put(option, true);
      }
    }
    return config;
  }


}
