package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;

import java.lang.reflect.Field;

public class TrackedNull implements TrackedField {
  private Field field;

  public TrackedNull(Field field) {
    this.field = field;
  }

  @Override
  public void accept(TrackService trackService) {
    trackService.visit(this);
  }

  public Field getField() {
    return this.field;
  }
}
