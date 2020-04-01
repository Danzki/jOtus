package com.danzki.gcTester;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {
  public static void main(String... args) throws Exception {
    GcInfoCollector youngInfo = new GcInfoCollector();
    GcInfoCollector oldInfo = new GcInfoCollector();
    Map<String, GcInfoCollector> gc = new HashMap<>();

    System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    switchOnMonitoring(gc);
    long beginTime = System.currentTimeMillis();

    int size = 5 * 1000 * 1000;
    int loopCounter = 100;
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName name = new ObjectName("com.danzki:type=runner");

    ObjectCreator mbean = new ObjectCreator(loopCounter);
    mbs.registerMBean(mbean, name);
    mbean.setSize(size);
    mbean.run();

    String elapsedTime = "time:" + (System.currentTimeMillis() - beginTime) / 1000;
    List<String> gcRows = new ArrayList<>();
    gc.forEach((k, v) -> gcRows.add(k + ": count = " + v.getCount() + ", time = " + v.getTime() / 1000 + " seconds"));

    System.out.println(elapsedTime);
    for (String row : gcRows) {
      System.out.println(row);
    }

    try (PrintStream out = new PrintStream(new FileOutputStream("./out/" + args[0] + ".txt"))) {
      out.println(args[0] + "info.");
      out.println(elapsedTime);
      for (String row : gcRows) {
        out.println(row);
      }
    }
  }

  private static void switchOnMonitoring(Map<String, GcInfoCollector> gc) {
    List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
    for (GarbageCollectorMXBean gcbean : gcbeans) {
      String key = keyByGenerationName(gcbean.getName());
      System.out.println("GC name:" + key);
      if (gc.get(key) == null) {
        gc.put(key, new GcInfoCollector());
      }
      NotificationEmitter emitter = (NotificationEmitter) gcbean;
      NotificationListener listener = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
          GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
          String gcName = info.getGcName();
          String gcAction = info.getGcAction();
          String gcCause = info.getGcCause();
          long startTime = info.getGcInfo().getStartTime();
          long duration = info.getGcInfo().getDuration();

          setGcInfo(gc.get(key), "Copy", gcName, duration);

          System.out.println("start:" + startTime +
              " Name:" + gcName +
              ", action:" + gcAction +
              ", gcCause:" + gcCause +
              "(" + duration + " ms)");
        }
      };
      emitter.addNotificationListener(listener, null, null);
    }
  }

  private static void setGcInfo(GcInfoCollector gcInfo, String gcNamePattern, String gcName, long duration) {
    gcInfo.setCount(gcInfo.getCount() + 1);
    gcInfo.setTime(gcInfo.getTime() + duration);
  }

  private static String keyByGenerationName(String name) {
    switch (name) {
      case ("Copy"): return "Young";
      case ("MarkSweepCompact"): return "Old";
      case ("PS MarkSweep"): return "Old";
      case ("PS Scavenge"): return "Young";
      case ("G1 Young Generation"): return "Young";
      case ("G1 Old Generation"): return "Old";
      case ("ParNew"): return "Young";
      case ("ConcurrentMarkSweep"): return "Old";
    }
    return "Empty";
  }
}
