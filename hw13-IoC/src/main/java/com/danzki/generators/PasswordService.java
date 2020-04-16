package com.danzki.generators;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {
  private String password;

  public PasswordService() {
    password = generatePassword();
  }

  public String generatePassword() {
    return "11111";
  }
}
