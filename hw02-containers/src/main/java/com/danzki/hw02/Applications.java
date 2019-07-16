package com.danzki.hw02;

import java.util.Comparator;

public class Applications implements Comparable<Applications> {
    private String appName;
    private String appType;
    private int licenseCount;

    public Applications(String appName, String appType, int licenseCount) {
        this.appName = appName;
        this.appType = appType;
        this.licenseCount = licenseCount;
    }

    @Override
    public int compareTo(Applications app) {
        return Comparators.APPNAME.compare(this, app);
    }

    public static class Comparators {
        public static final Comparator<Applications> APPNAME = (Applications app1, Applications app2) -> app1.appName.compareTo(app2.appName);
    }
}
