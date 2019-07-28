package com.danzki.hw04;

import com.danzki.hw04.Interfaces.ApplicationInterface;

public class ProxuRunner {
  public static void main(String[] args) {
    ApplicationInterface application = IoC.createProxy();
    application.applicationList(10);
  }
}
