package com.danzki.hw03.TestingEnvironment;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestingEnv {

  private static void annotatedMethodLoop(Object obj, Class<?> aClass, Class<?> annotation)
      throws InvocationTargetException, IllegalAccessException {
    for (Method method : aClass.getDeclaredMethods()) {
      if (method.isAnnotationPresent((Class<? extends Annotation>) annotation)) {
        try {
          method.setAccessible(true);
          method.invoke(obj);
        } finally {
          method.setAccessible(false);
        }
      }
    }
  }

  public static void main(String[] args)
      throws IllegalAccessException,
      NoSuchMethodException,
      InvocationTargetException,
      InstantiationException {
    Class<?> aClass = null;
    if (args.length > 0) {
      try {
        aClass = Class.forName(args[0]);
      } catch (ClassNotFoundException e) {
        System.out.println("Wrong class name.");
        e.printStackTrace();
      }
    }

    int failTests = 0;
    int successTests = 0;

    if (aClass != null) {
      for (Method method : aClass.getDeclaredMethods()) {
        if (method.isAnnotationPresent(Test.class)) {
          Object instance = aClass.getDeclaredConstructor().newInstance();
          annotatedMethodLoop(instance, aClass, Before.class);
          try {
            try {
              method.setAccessible(true);
              method.invoke(instance);
            } finally {
              method.setAccessible(false);
            }
            successTests++;
          } catch (InvocationTargetException e) {
            failTests++;
            e.printStackTrace();
          }
          annotatedMethodLoop(instance, aClass, After.class);
        }
      }
    }

    System.out.println("Statistic of running of " + (successTests + failTests) + " tests:");
    System.out.println("Successful tests: " + successTests);
    System.out.println("Failed tests: " + failTests);
  }
}
