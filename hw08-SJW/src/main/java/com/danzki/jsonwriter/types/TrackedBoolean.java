package com.danzki.jsonwriter.types;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.TrackedField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

@AllArgsConstructor
public class TrackedBoolean implements TrackedField {
  @Getter
  private Field field;
  @Getter
  private Object object;

  @Override
  @SneakyThrows
  public void accept(TrackService trackService) {
    trackService.visit(this);
  }
}
