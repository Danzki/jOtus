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

  public void setTime(double time) {
    this.time = time;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
