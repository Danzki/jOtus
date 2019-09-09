package com.danzki.jsonwriter;

import com.danzki.jsonwriter.classes.examples.Employee;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;

@AllArgsConstructor
public class EmpToJson {
  Employee employee;

  public JsonObject create() {
    int i = 0;
    var jsonArrayBuilder = Json.createArrayBuilder();
    for (String param : employee.getParams()) {
      jsonArrayBuilder.add(Json.createObjectBuilder()
          .add("params" + i++, param));
    }

    var jsonObject = Json.createObjectBuilder()
        .add("isWork", employee.isWork())
        .add("firstName", employee.getFirstName())
        .add("lastName", employee.getLastName())
        .add("age", employee.getAge())
        .add("castaId", employee.getCastaId())
        .add("params", jsonArrayBuilder)
        .build();

    return jsonObject;
  }
}
