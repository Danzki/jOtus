package com.danzki.services;

public class PasswordService {
  private String password;

  public PasswordService() {
    password = generatePassword();
  }

  public String generatePassword() {
    return "11111";
  }
}
