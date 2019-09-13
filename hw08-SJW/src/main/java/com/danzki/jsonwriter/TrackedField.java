package com.danzki.jsonwriter;

public interface TrackedField {
  void accept(TrackService trackService) throws IllegalAccessException;
}
