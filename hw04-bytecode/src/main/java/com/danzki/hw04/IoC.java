package com.danzki.hw04;

import com.danzki.hw04.Annotations.Log;
import com.danzki.hw04.Interfaces.ApplicationInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;

public class IoC {
  static ApplicationInterface createProxy() {
    InvocationHandler handler = new MyInvocationHandler(new ApplicationImpl());
    return (ApplicationInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
        new Class<?>[]{ApplicationInterface.class},
        handler);
  }

  private final static class MyInvocationHandler implements InvocationHandler {
    Object appClass;
    HashSet<String> methods;

    MyInvocationHandler(ApplicationInterface appClass) {
      this.appClass = appClass;
      methods = new HashSet<>();
      for (Method method : this.appClass.getClass().getDeclaredMethods()) {
        if (method.isAnnotationPresent(Log.class)) {
          methods.add(method.getName());
        }
      }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (methods.contains(method.getName())) {
        System.out.print("executed method:" + method.getName() + ", ");
        int c = 0;
        for (Object arg : args) {
          System.out.print("arg" + c++ + ": " + arg);
        }
        System.out.println("");
      }
      return method.invoke(appClass, args);
    }
  }

}
