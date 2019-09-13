package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class TrackedBoolean implements TrackedField {
  private Field field;
  private Object object;

  public TrackedBoolean(Field field, Object object) {
    this.field = field;
    this.object = object;
  }

  @Override
  @SneakyThrows
  public void accept(TrackService trackService) {
    trackService.visit(this);
  }

  public Field getField() {
    return this.field;
  }

  public Object getObject() {
    return this.object;
  }
}
