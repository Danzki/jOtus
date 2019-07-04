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

    private boolean isRepeated = true;
    private List<String> pinsToReturn = Lists.newArrayList();


    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }


    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public List<String> getPinsToReturn() {
        if (this.pinsToReturn.isEmpty()) {
            this.pinsToReturn = generatePinList(this.maxCount);
        }
        List<String> repeatedPins = this.pinsToReturn;
        return repeatedPins;
    }

    private List<String> generatePinList(int max) {
        int min = 1;
        List<String> pins = Lists.newArrayList();

        //for (int i = min; i < max; i++) {
        while (true) {
            boolean repeat = false;

            String pin = generatePin();
            for (int j = 0; j < pin.length(); j++) {
                char c = pin.charAt(j);
                int count = CharMatcher.is(c).countIn(pin);
                if (count > 1) {
                    repeat = true;
                }
            }
            if (this.isRepeated) {
                pins.add(pin);
                if (pins.size() == max) {
                    break;
                }
            } else if (!this.isRepeated && !repeat) {
                pins.add(pin);
                if (pins.size() == max) {
                    break;
                }
            }

        }
        return pins;
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
        HelloOtus ho = new HelloOtus();

        String s;
        Scanner sc = new Scanner(System.in);
        int count = 0;
        System.out.println("Enter a max count of Pin list (more than 0):");
        while (true) {
            if (sc.hasNextInt()) {
                count = Integer.valueOf(sc.nextLine());
                ho.setMaxCount(count);
                if (count > 0) {
                    break;
                } else {
                    System.out.println("Please enter only numbers more than 0:");
                }

            } else {
                System.out.println("Please enter only numbers more than 0:");
                sc.nextLine();
            }
        }

        System.out.println("Do you want to get pins with repeated digits? (y/n)");
        while (true) {
            s = sc.nextLine();
            if (s.toUpperCase().equals("Y")) {
                ho.setRepeated(true);
                break;
            } else if (s.toUpperCase().equals("N")) {
                ho.setRepeated(false);
                break;
            } else {
                System.out.println("Sorry, please enter y/n:");
            }
        }

        List<String> pins = ho.getPinsToReturn();

        System.out.println("List of pins with repeated digits is: ");
        for(String item : pins){
            System.out.println(item);
        }
        System.out.println("Quantity of pins " +
                            (ho.isRepeated ? "with repeated digits " : "without repeated digits ") +
                            pins.size());
    }
}
