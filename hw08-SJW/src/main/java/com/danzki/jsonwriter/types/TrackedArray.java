package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;

import java.lang.reflect.Field;

public class TrackedArray implements TrackedField {
  private Field field;

  private Object object;

  public TrackedArray(Field field, Object object) {
    this.field = field;
    this.object = object;
  }

  @Override
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
