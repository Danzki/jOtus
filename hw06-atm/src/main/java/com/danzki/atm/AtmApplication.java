package com.danzki.atm;

import com.danzki.atm.exceptions.IncorrectAmount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class AtmApplication {
  public static void main(String[] args) throws IOException, IncorrectAmount {
    var atm = new Atm();
    atm.loadAtm();

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      // Enter the message.
      System.out.print("Input Cash amount (enter q to exit): ");
      String message = reader.readLine();

      if (message.equals("q")) {
        break;
      }

      int requestedAmount = 0;
      try {
        requestedAmount = Integer.parseInt(message);
      } catch (NumberFormatException e) {
        System.out.println("Incorrect amount entered. Please try again");
      }

      Map<Integer, Integer> returnBanknotes = atm.giveCash(requestedAmount);
      System.out.println("Please receive your cash with next banknotes: ");
      returnBanknotes.forEach((k, v) -> System.out.println(v + " of " + k + " nominal"));

    }

    System.out.println("Statement of cash in ATM: " + atm.getCurrentStatement());

  }
}
