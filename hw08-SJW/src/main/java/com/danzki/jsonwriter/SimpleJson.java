package com.danzki.jsonwriter;

import javax.json.JsonObject;

public interface SimpleJson {
  JsonObject toJson(Object object) throws IllegalAccessException;
}
