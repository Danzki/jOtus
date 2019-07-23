package com.danzki.hw03;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestingEnv {

  private static void annotatedMethodLoop(Object obj, Class<?> aClass, Class<?> annotation) throws InvocationTargetException, IllegalAccessException {
    for (Method method : aClass.getMethods()) {
      if (method.isAnnotationPresent((Class<? extends Annotation>) annotation)) {
        method.invoke(obj);
      }
    }
  }

  public static void main(String[] args) throws ClassNotFoundException,
                                                InvocationTargetException,
                                                IllegalAccessException,
                                                NoSuchMethodException,
                                                InstantiationException
  {
    Class<?> aClass = Class.forName(args[0]);

    for (Method method : aClass.getMethods()) {
      if (method.isAnnotationPresent(Test.class)) {
        Object instance = aClass.getDeclaredConstructor().newInstance();
        annotatedMethodLoop(instance, aClass, Before.class);
        method.invoke(instance);
        annotatedMethodLoop(instance, aClass, After.class);
      }
    }
  }
}
