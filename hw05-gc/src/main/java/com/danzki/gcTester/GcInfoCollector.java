package com.danzki.gcTester;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GcInfoCollector {
  private int count;
  private double time;
  private double maxPause = 0;

  public void setTime(double time) {
    this.time = time;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setMaxPause(double maxPause) {
    if (this.maxPause < maxPause) {
      this.maxPause = maxPause;
    }
  }
}
