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
        Applications[] appArray = {};
        for(int i = 0; i < arrSize; i++) {
            appArray[i] = new Applications("App"+i, "Software", 60);

        }
        Collections.addAll(apps, appArray);
        System.out.println("Application array size is "+apps.size());

        //Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        List<Applications> copyOfApps = new DIYarrayList<>(arrSize);
        Collections.copy(copyOfApps, apps);
        System.out.println("Copy of Application array size is"+apps.size());

        //Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(apps, Applications.Comparators.APPNAME);
        
    }
}
