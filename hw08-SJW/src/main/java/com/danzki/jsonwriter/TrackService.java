package com.danzki.jsonwriter;

import com.danzki.jsonwriter.types.TrackedArray;
import com.danzki.jsonwriter.types.TrackedObject;
import com.danzki.jsonwriter.types.TrackedPrimitive;
import com.danzki.jsonwriter.types.TrackedString;

public interface TrackService {
  void visit(TrackedArray field);
  void visit(TrackedPrimitive field);
  void visit(TrackedObject field);
  void visit(TrackedString field);

  void stepIn();
  void stepOut();
  void openObject();
  void closeObject();
  String getResult();
}
