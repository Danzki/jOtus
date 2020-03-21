package com.danzki.jdbc;

import com.danzki.core.annotations.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Mapper<T> {
  private String tableName;
  private Field primaryKey;
  private List<Field> fields;

  public Mapper(Class<T> clazz) {
    tableName = clazz.getSimpleName();
    fields = new ArrayList<>();
    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(Id.class)) {
        primaryKey = field;
      } else {
        fields.add(field);
      }
    }
  }

  public long getPrimaryKeyId(Object object) throws IllegalAccessException {
    primaryKey.setAccessible(true);
    return (long) primaryKey.get(object);
  }

  private void objectParser(Object object) {
    tableName = object.getClass().getSimpleName();
    fields = new ArrayList<>();
    for (Field field : object.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(Id.class)) {
        primaryKey = field;
      } else {
        fields.add(field);
      }
    }
  }

  public String getSelectQuery() {
    var sb = new StringBuilder();
    sb.append("select ");
    sb.append(primaryKey.getName());
    for (Field field : fields) {
      sb.append(", ");
      sb.append(field.getName());
    }
    sb.append(" from ");
    sb.append(tableName);
    sb.append(" where ");
    sb.append(primaryKey.getName());
    sb.append(" = ?");
    return sb.toString();
  }

  public String getInsertQuery() {
    var fieldNamesSb = new StringBuilder();
    var fieldValuesSb = new StringBuilder();
    for(Field field : fields) {
      fieldNamesSb.append(field.getName()).append(",");
      fieldValuesSb.append("?,");
    }
    fieldNamesSb.deleteCharAt(fieldNamesSb.length() - 1);
    fieldValuesSb.deleteCharAt(fieldValuesSb.length() - 1);

    var sb = new StringBuilder();
    sb.append("insert into ");
    sb.append(tableName);
    sb.append("(");
    sb.append(fieldNamesSb.toString());
    sb.append(") values (");
    sb.append(fieldValuesSb.toString());
    sb.append(")");
    return sb.toString();
  }

  public String getUpdateQuery(Object object) throws IllegalAccessException {
    var sb = new StringBuilder();
    sb.append("update ");
    sb.append(tableName);
    sb.append(" set ");
    for (Field field : fields) {
      sb.append(field.getName());
      sb.append(" = ");
      field.setAccessible(true);
      if (field.getType().isAssignableFrom(String.class)) {
        sb.append("'");
        sb.append(field.get(object));
        sb.append("'");
      } else if (field.getType().isAssignableFrom(Integer.class) || field.getType().getName() == "int") {
        sb.append(field.get(object));
      }
      sb.append(",");
    }
    sb.delete(sb.length()-1, sb.length());
    sb.append(" where ");
    sb.append(primaryKey.getName());
    sb.append(" = ?");
    return sb.toString();
  }
}
