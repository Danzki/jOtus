package com.danzki.jsonwriter;

public interface SimpleJson {
  String toJson(Object object) throws IllegalAccessException;
  String toJson(String string);
  String toJson(int number);
  String toJson(long number);
  String toJson(float number);
  String toJson(double number);
  String toJson(short number);
  String toJson(byte number);
  String toJson(boolean bool);
  String toJson(char c);
}
