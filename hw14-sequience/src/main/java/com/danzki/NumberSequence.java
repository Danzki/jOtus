package com.danzki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class NumberSequence {
  private static final Logger logger = LoggerFactory.getLogger(NumberSequence.class);

  private final int SWITCH_VALUES = 10;

  AtomicInteger counter1 = new AtomicInteger(1);
  AtomicInteger counter2 = new AtomicInteger(1);

  Thread t1;
  Thread t2;

  private Boolean direct = true;  //true - increase, false - decrease

  public NumberSequence() {
    t1 = new Thread(() -> action(counter1));
    t2 = new Thread(() -> action(counter2));

    t1.setName("First");
    t2.setName("Second");
  }


  private synchronized void action(AtomicInteger counter) {
    while (true) {
      if (isCounterOutofRange(counter)) {
        counter.set(SWITCH_VALUES);
        direct = false;
      }

      if (counter.get() == 1) {
        direct = true;
      }

      logger.info("Thread {}: {}", Thread.currentThread().getName()
          , direct ? counter.getAndIncrement() : counter.getAndDecrement());

      if (areThreadsWaiting()) {
        notifyAll();
      }

      localSleep(100);
      try {
        wait();
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
        ex.printStackTrace();
      }
    }
  }

  private void localSleep(int ms) {
    try {
      sleep(ms);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  private Boolean isCounterOutofRange(AtomicInteger counter) {
    if (counter.get() == SWITCH_VALUES) {
      return true;
    }
    return false;
  }

  private Boolean areThreadsWaiting() {
    return t1.getState().equals(Thread.State.WAITING) || t2.getState().equals(Thread.State.WAITING);
  }

  public static void main(String[] args) {
    NumberSequence numberSequence = new NumberSequence();

    numberSequence.t1.start();
    numberSequence.t2.start();
  }
}
