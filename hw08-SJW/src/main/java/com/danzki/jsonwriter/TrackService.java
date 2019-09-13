package com.danzki.jsonwriter;

import com.danzki.jsonwriter.types.*;

import javax.json.JsonObject;

public interface TrackService {
  void visit(TrackedArray field);
  void visit(TrackedPrimitive field) throws IllegalAccessException;
  void visit(TrackedString field);
  void visit(TrackedBoolean field) throws IllegalAccessException;
  void visit(TrackedNull field);

  JsonObject jsonBuild();
}
