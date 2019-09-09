package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.*;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.json.Json;
import javax.json.JsonObject;

public class JsonService implements TrackService {
  @Getter
  private JsonObject jsonObject;

  @Override
  public void visit(TrackedArray field) {

  }

  @Override
  @SneakyThrows
  public void visit(TrackedPrimitive field) {
    jsonObject = Json.createObjectBuilder()
        .add(field.getField().getName(), field.getField().getInt(field.getObject()))
        .build();
  }

  @Override
  public void visit(TrackedObject field) {

  }

  @Override
  public void visit(TrackedString field) {
    jsonObject = Json.createObjectBuilder()
        .add(field.getField().getName(), String.valueOf(field.getField()))
        .build();
  }

  @Override
  @SneakyThrows
  public void visit(TrackedBoolean field) {
    jsonObject = Json.createObjectBuilder()
        .add(field.getField().getName(), field.getField().getBoolean(field.getObject()) ? "true" : "false")
        .build();
  }

  @Override
  public void visit(TrackedNull field) {
    jsonObject = Json.createObjectBuilder()
        .add(field.getField().getName(), "null")
        .build();
  }


}
