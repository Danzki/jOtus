package com.danzki.gcTester;

import java.util.ArrayList;
import java.util.List;

public class ObjectCreator implements ObjectCreatorMBean {
  private int size;
  private int loopCounter;

  public ObjectCreator(int loopCounter) {
    this.loopCounter = loopCounter;
  }

  public void run() throws InterruptedException {
    for( int i = 0; i < loopCounter; i++) {
      int localSize = size;
      List<Object> arr = new ArrayList<>();
      for (int j = 0; j < localSize; j++) {
        arr.add(new String(new char[0]));
        if (j > 0 && j % 5 == 0) {
          arr.remove(arr.size() - 1);
        }
      }
      Thread.sleep(10);
    }
  }


  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public void setSize(int size) {
    this.size = size;
  }
}
