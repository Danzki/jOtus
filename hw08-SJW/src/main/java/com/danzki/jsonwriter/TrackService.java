package com.danzki.jsonwriter;

import com.danzki.jsonwriter.types.*;

public interface TrackService {
  void visit(TrackedArray field);
  void visit(TrackedPrimitive field);
  void visit(TrackedObject field);
  void visit(TrackedString field);
  void visit(TrackedBoolean field);
  void visit(TrackedNull field);
}
