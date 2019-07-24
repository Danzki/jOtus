package com.danzki.hw03.TestingEnvironment;

import com.danzki.hw03.TestingEnvironment.annotations.After;
import com.danzki.hw03.TestingEnvironment.annotations.Before;
import com.danzki.hw03.TestingEnvironment.annotations.Test;
import com.danzki.hw03.TestingEnvironment.exceptions.MyTestFrameworkException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestingEnv {

  private Class<?> aClass = null;

  public TestingEnv() {
  }

  private Map<String, List<Method>> methods = new HashMap<>();

  public TestingEnv(Class<?> aClass) {
    this.aClass = aClass;
  }

  public Class<?> getaClass() {
    return aClass;
  }

  private Map<String, List<Method>> getMethods() {
    if (aClass != null) {
      for (Method method : aClass.getDeclaredMethods()) {
        mapMethodWithAnnotation(method, Test.class);
        mapMethodWithAnnotation(method, Before.class);
        mapMethodWithAnnotation(method, After.class);
      }
    }

    return methods;
  }

  private void mapMethodWithAnnotation(Method method, Class<? extends Annotation> annotationClass) {
    if (method.isAnnotationPresent(annotationClass)) {
      methods.computeIfAbsent(annotationClass.getName(), k -> new ArrayList<>()).add(method);
    }
  }

  public List<Method> getMethodsByAnnotation(Class<? extends Annotation> annotationClass) {
    if (methods.size() == 0) {
      methods = getMethods();
    }
    List<Method> annoMethods = new ArrayList<>();
    for (Map.Entry<String, List<Method>> entry : methods.entrySet()) {
      if (entry.getKey().equals(annotationClass.getName())) {
        annoMethods = entry.getValue();
      }
    }
    return annoMethods;
  }

  public void executeMethods(Object obj, Class<? extends Annotation> annotation)
      throws MyTestFrameworkException {
    for (Method method : getMethodsByAnnotation(annotation)) {
      try {
        method.setAccessible(true);
        method.invoke(obj);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new MyTestFrameworkException(e);
      } finally {
        method.setAccessible(false);
      }
    }
  }

}
