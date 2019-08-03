package com.danzki.hw04;

import com.danzki.hw04.Annotations.Log;
import com.danzki.hw04.Interfaces.ApplicationInterface;

public class ApplicationImpl implements ApplicationInterface {

  public ApplicationImpl() {
  }

  @Override
  @Log
  public void applicationList(int appCount) {
    System.out.println("applicationList method is running. There are " + appCount + " applications.");
  }
}
