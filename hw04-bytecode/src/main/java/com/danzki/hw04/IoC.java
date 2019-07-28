package com.danzki.hw04;

import com.danzki.hw04.Interfaces.ApplicationInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IoC {
  static ApplicationInterface createProxy() {
    InvocationHandler handler = new MyInvocationHandler(new ApplicationImpl());
    return (ApplicationInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
      new Class<?>[]{ApplicationInterface.class}, handler );
  }

  static class MyInvocationHandler implements InvocationHandler {
    private final ApplicationInterface appClass;

    MyInvocationHandler(ApplicationInterface appClass) {
      this.appClass = appClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.print("executed method:" + method.getName() + ", ");
      int c = 0;
      for (Object arg : args) {
        System.out.print("arg" + c++ + ": " + arg);
      }
      System.out.println("");
      return method.invoke(appClass, args);
    }
  }

}
