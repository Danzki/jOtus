package com.danzki.hw03;

import com.danzki.hw03.TestingEnvironment.annotations.After;
import com.danzki.hw03.TestingEnvironment.annotations.Before;
import com.danzki.hw03.TestingEnvironment.annotations.Test;
import com.danzki.hw03.TestingEnvironment.TestingEnv;
import com.danzki.hw03.TestingEnvironment.exceptions.MyTestFrameworkException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {

  public static void run(Class<?> aClass) throws MyTestFrameworkException {
    TestingEnv testingEnv = new TestingEnv(aClass);
    int failTests = 0;
    int successTests = 0;

    for (Method method : testingEnv.getMethodsByAnnotation(Test.class)) {
      try {
        Object instance = testingEnv.getaClass().getDeclaredConstructor().newInstance();
        //запустить все методы Before
        testingEnv.executeMethods(instance, Before.class);
        //запустить один метод Test
        method.setAccessible(true);
        method.invoke(instance);
        successTests++;
        //запустить все метода After
        testingEnv.executeMethods(instance, After.class);
      } catch (IllegalAccessException | IllegalArgumentException |
          InvocationTargetException | NoSuchMethodException | SecurityException |
          InstantiationException e) {
        failTests++;
      } finally {
        method.setAccessible(false);
      }
    }

    System.out.println("Statistic of running of " + (successTests + failTests) + " tests:");
    System.out.println("Successful tests: " + successTests);
    System.out.println("Failed tests: " + failTests);


  }

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
        run(aClass);
      } catch (MyTestFrameworkException e) {
        e.printStackTrace();
      }
    }
  }
}
