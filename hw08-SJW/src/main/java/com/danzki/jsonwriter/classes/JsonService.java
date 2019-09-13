package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonService implements TrackService {
  private JsonObject jsonObject;

  private List<JsonObjectBuilder> jsonBuilders;

  public JsonService() {
    jsonBuilders = new ArrayList<>();
  }

  @Override
  public void visit(TrackedArray trackedArray) {
    Object arrayObj = trackedArray.getObject();
    int length = Array.getLength(arrayObj);
    var jsonArrayBuilder = Json.createArrayBuilder();
    for (int i = 0; i < length; i++) {
      Object value = Array.get(arrayObj, i);
      jsonArrayBuilder.add(String.valueOf(value));
    }

    var jsonBuilder = Json.createObjectBuilder()
        .add(trackedArray.getField().getName(), jsonArrayBuilder);
    jsonBuilders.add(jsonBuilder);
  }

  @Override
  public void visit(TrackedPrimitive trackedPrimitive) throws IllegalAccessException {
    String name = trackedPrimitive.getField().getType().getName();
    var jsonBuilder = Json.createObjectBuilder();
    if (name.equals("int") ||
        name.equals("long") ||
        name.equals("short") ||
        name.equals("byte")
    ) {
      jsonBuilder.add(trackedPrimitive
                  .getField()
                  .getName(),
              trackedPrimitive
                  .getField()
                  .getInt(trackedPrimitive.getObject())
      );
    } else if (name.equals("boolean") ||
        name.equals("java.lang.Boolean")) {
      jsonBuilder.add(trackedPrimitive
              .getField()
              .getName(),
              trackedPrimitive
                  .getField()
                  .getBoolean(trackedPrimitive.getObject()) ? JsonValue.TRUE : JsonValue.FALSE);
    }
    jsonBuilders.add(jsonBuilder);
  }

  @Override
  public void visit(TrackedString trackedString) {
    var jsonBuilder = Json.createObjectBuilder()
        .add(trackedString
                  .getField()
                  .getName(),
              String.valueOf(trackedString
                  .getObject()));
    jsonBuilders.add(jsonBuilder);
  }

  @Override
  public void visit(TrackedBoolean trackedBoolean) throws IllegalAccessException {
    var jsonBuilder = Json.createObjectBuilder()
        .add(trackedBoolean
            .getField()
            .getName(),
            (Boolean) trackedBoolean.getObject() ? JsonValue.TRUE : JsonValue.FALSE);
    jsonBuilders.add(jsonBuilder);
  }

  @Override
  public void visit(TrackedNull trackedNull) {
    var jsonBuilder = Json.createObjectBuilder()
        .add(trackedNull
            .getField()
            .getName(),
        JsonValue.NULL);
    jsonBuilders.add(jsonBuilder);
  }

  @Override
  public JsonObject jsonBuild() {
    var jsonBuilder = Json.createObjectBuilder();
    for (JsonObjectBuilder jb : jsonBuilders) {
      jsonBuilder.addAll(jb);
    }
    jsonObject = jsonBuilder.build();

    return jsonObject;
  }

  public JsonObject getJsonObject() {
    return this.jsonObject;
  }
}
