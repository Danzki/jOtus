package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

@AllArgsConstructor
public class TrackedObject implements TrackedField {
  @Getter
  private Field field;

  @Getter
  private Object object;

  @Override
  public void accept(TrackService trackService) {
    trackService.visit(this);
  }
}
