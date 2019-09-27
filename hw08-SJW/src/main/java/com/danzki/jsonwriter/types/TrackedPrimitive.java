package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;

import java.lang.reflect.Field;

public class TrackedPrimitive implements TrackedField {
  private Field field;

  private Object object;

  public TrackedPrimitive(Field field, Object object) {
    this.field = field;
    this.object = object;
  }

  @Override
  public void accept(TrackService trackService) throws IllegalAccessException {
    trackService.visit(this);
  }

  public Field getField() {
    return this.field;
  }

  public Object getObject() {
    return object;
  }
}
