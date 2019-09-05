package com.danzki.jsonwriter.classes;

import com.danzki.jsonwriter.TrackService;
import com.danzki.jsonwriter.types.TrackedArray;
import com.danzki.jsonwriter.types.TrackedObject;
import com.danzki.jsonwriter.types.TrackedPrimitive;
import com.danzki.jsonwriter.types.TrackedString;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class JsonService implements TrackService {
  private String json;
  private int level;
  private final String INTEND = "  ";
  private final String MARKER = "\"";
  private final String DELIM = ": ";
  private final String VALUE_END = ",\n";
  private final String OBJ_BEG = "{\n";
  private final String OBJ_END = "\n}";
  private final String ARR_BEG = "[\n";
  private final String ARR_END = "\n]";

  public JsonService(int level) {
    this.level = level;
  }

  public void stepIn() {
    this.level += 1;
  }

  public void stepOut() {
    this.level -= 1;
  }

  public void openObject() {
    json = OBJ_BEG;
  }

  public void closeObject() {
    json = json.substring(0, json.length()-OBJ_END.length()) + OBJ_END;
  }

  public String getResult() {
    return json;
  }

  private void openArray() {
    json += ARR_BEG;
  }

  private void closeArray() {
    json = json.substring(0, json.length()-ARR_END.length()) + ARR_END;
  }

  private String getIntend() {
    return StringUtils.repeat(INTEND, level);
  }

  private String getDelim() {
    return DELIM;
  }

  private String getValue(Object value) {
    if (value.getClass().equals(String.class)) {
      return MARKER + value + MARKER + VALUE_END;
    }
    return value != null ? value + VALUE_END : "null" + VALUE_END ;
  }

  private String getFieldKey(String key) {
    return json +
        getIntend() +
        key +
        getDelim();
  }

  @Override
  public void visit(TrackedArray field) {
    json = getFieldKey(field.getField().getName());
    openArray();

    closeArray();
  }

  @Override
  @SneakyThrows
  public void visit(TrackedPrimitive field) {
    json = getFieldKey(field.getField().getName()) +
        getValue(field.getField().get(field.getObject()));
  }

  @Override
  public void visit(TrackedObject field) {

  }

  @Override
  @SneakyThrows
  public void visit(TrackedString field) {
    json = getFieldKey(field.getField().getName()) +
        getValue(field.getField().get(field.getObject()));
  }
}
