package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.annotations.After;
import com.danzki.hw03.TestingEnvironment.annotations.Before;
import com.danzki.hw03.TestingEnvironment.annotations.Test;
import com.danzki.hw03.TestingEnvironment.TestingEnv;
import com.danzki.hw03.TestingEnvironment.exceptions.MyTestFrameworkException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        TestingEnv testingEnv = new TestingEnv(aClass);
        testingEnv.run(aClass);
      } catch (MyTestFrameworkException e) {
        e.printStackTrace();
      }
    }
  }
}
