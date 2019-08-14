package com.danzki.atm;

import java.util.Comparator;

public enum Banknote {
    HUNDRED (100),
    TWOHUNDRED (200),
    FIVEHUNDRED (500),
    THOUSAND (1000),
    TWOTHOUSAND (2000),
    FIVETHOUSAND (5000);

    private Integer nominal;

    Banknote(Integer nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }

    public static Comparator<Banknote> nominalComparator = new Comparator<Banknote>() {
        @Override
        public int compare(Banknote o1, Banknote o2) {
            return o2.getNominal() - o1.getNominal();
        }
    };
}
