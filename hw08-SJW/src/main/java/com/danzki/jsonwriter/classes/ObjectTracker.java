package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.*;
import lombok.SneakyThrows;

import javax.json.JsonObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

public class ObjectTracker {

  @SneakyThrows
  public JsonObject trackMethod(Field mainfield, Object object, TrackService service) {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      Object fieldObject = field.get(object);
      if (fieldObject == null) {
        new TrackedNull(field).accept(service);
      } else if (field.getType().isPrimitive()) {
        new TrackedPrimitive(field, object).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new TrackedString(field, fieldObject).accept(service);
      } else if (field.getType().isAssignableFrom(Boolean.class)) {
        new TrackedBoolean(field, fieldObject).accept(service);
      } else if (field.getType().isAssignableFrom(List.class)) {
        new TrackedArray(field, ((Collection) fieldObject).toArray()).accept(service);
      } else if (field.getType().isArray()) {
        new TrackedArray(field, fieldObject).accept(service);
      } else {
        trackMethod(field, object, service);
      }
    }

    return service.jsonBuild();
  }
}
