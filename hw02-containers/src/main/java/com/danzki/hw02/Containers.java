package com.danzki.hw02;

import java.util.Collections;
import java.util.List;

public class Containers {

    public static void main(String[] args) {

        //Проверяйте на коллекциях с 20 и больше элементами.
        int arrSize = 40;

        //Проверить, что на ней работают методы из java.util.Collections:
        //Collections.addAll(Collection<? super T> c, T... elements)
        List<Applications> apps = new DIYarrayList<>(arrSize);
        List<Applications> copyOfApps = new DIYarrayList<>(arrSize);
        for(int i = 0; i < arrSize; i++) {
            Collections.addAll(apps,
                            new Applications("App"+i, "Software", 60));

            Collections.addAll(copyOfApps,
                    new Applications("Empty"+i, "Empty", 60));
        }

        //Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        Collections.copy(copyOfApps, apps);

        //Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(apps, Applications.Comparators.APPNAME);

    }
}
