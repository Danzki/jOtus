package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.TrackedArray;
import com.danzki.jsonwriter.types.TrackedObject;
import com.danzki.jsonwriter.types.TrackedPrimitive;
import com.danzki.jsonwriter.types.TrackedString;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectTracker {

  public void trackMethod(Field mainField, Object object, TrackService service) {
    if (object.getClass().isArray()) {
      new TrackedArray(mainField, object).accept(service);
    } else {
      new TrackedObject(mainField, object).accept(service);
    }

    Field[] fields = object.getClass().getDeclaredFields();
    if (fields.length > 0) {
      service.stepIn();
      service.openObject();
    }
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      if (field.getType().isPrimitive()) {
        new TrackedPrimitive(field, object).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new TrackedString(field, object).accept(service);
      } else if (field.getType().isArray()) {
        new TrackedArray(field, object).accept(service);
//      } else if (field.getType().is) {

      } else {
        trackMethod(field, object, service);
      }
    }
    service.stepOut();
    if (fields.length > 0)
      service.closeObject();
  }
}
