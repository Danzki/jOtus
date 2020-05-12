package com.danzki.backend.generators;

import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
  private String password;

  public PasswordServiceImpl() {
    password = generatePassword();
  }

  public String generatePassword() {
    return "11111";
  }
}
