package com.danzki.hw01;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by dan.kapustin
 *
 * Generates MAX_PIN_LENGTH pins (string of 4 digits) and prints percent of pins with repeated digits.
 *
 * Example:
 * Input:
 * 1156
 * 2345
 *
 * Output:
 * List of pins with repeated digits is:
 * 1156
 * Quantity of pins with repeated digits is 1.
 *
 *
 *
 */


public class HelloOtus {
    private static final int MAX_PIN_LENGTH = 4;

    private int maxCount = 1000;
    private List<String> repeatedPins = Lists.newArrayList();

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public List<String> getRepeatedPins() {
        if (this.repeatedPins.isEmpty()) {
            this.repeatedPins = generatePinList(this.maxCount);
        }
        List<String> repeatedPins = this.repeatedPins;
        return repeatedPins;
    }

    private List<String> generatePinList(int max) {
        int min = 1;
        List<String> repeatedPins = Lists.newArrayList();

        for (int i = min; i < max; i++) {
            String pin = generatePin();
            for (int j = 0; j < pin.length(); j++) {
                char c = pin.charAt(j);
                int count = CharMatcher.is(c).countIn(pin);
                if (count > 1) {
                    repeatedPins.add(pin);
                    break;
                }
            }
        }
        return repeatedPins;
    }

    private static String generatePin() {
        String pin = new String();

        for(int i = 0; i < MAX_PIN_LENGTH; i++) {
            int digit = getRandomInt(0, 9);
            pin = pin + digit;
        }

        return pin;
    }

    private static int getRandomInt(int min, int max) {
        Random r = new Random();
        return r.ints(min, max).limit(1).findFirst().getAsInt();
    }


    public static void main(String[] args) {
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a max count of Pin list");
        s = sc.nextLine();

        HelloOtus ho = new HelloOtus();
        ho.setMaxCount(Integer.valueOf(s));

        List<String> pins = ho.getRepeatedPins();

        System.out.println("List of pins with repeated digits is: ");
        for(String item : pins){
            System.out.println(item);
        }
        System.out.println("Quantity of pins with repeated digits is "+pins.size());

    }
}
