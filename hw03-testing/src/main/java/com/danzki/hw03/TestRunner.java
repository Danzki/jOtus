package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.TestingEnv;
import com.danzki.hw03.TestingEnvironment.exceptions.MyTestFrameworkException;

public class TestRunner {

  public static void main(String[] args) {
    Class<?> aClass = null;
    if (args.length > 0) {
      try {
        aClass = Class.forName(args[0]);
      } catch (ClassNotFoundException e) {
        System.out.println("Wrong class name.");
        e.printStackTrace();
      }
    }

    if (aClass != null) {
      try {
        TestingEnv.run(aClass);
      } catch (MyTestFrameworkException e) {
        e.printStackTrace();
      }
    }
  }
}
