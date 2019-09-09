package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.*;

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
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      if (field.getType().isPrimitive()) {
        new TrackedPrimitive(field, object).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new TrackedString(field, object).accept(service);
      } else if (field.getType().isAssignableFrom(Boolean.class)) {
        new TrackedBoolean(field, object).accept(service);
      } else if (field.getType().isArray()) {
        new TrackedArray(field, object).accept(service);
      } else if (field == null) {
        new TrackedNull(field, object).accept(service);
      } else {
        trackMethod(field, object, service);
      }
    }

//    System.out.println(service.getResult());
  }
}
