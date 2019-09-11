package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

@AllArgsConstructor
public class TrackedNull implements TrackedField {
  @Getter
  private Field field;

  @Override
  public void accept(TrackService trackService) {
    trackService.visit(this);
  }
}
