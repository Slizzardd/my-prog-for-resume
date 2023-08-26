package ua.com.alevel.util;

import java.util.Random;

public final class GenerateDataCard {

    private GenerateDataCard() {
        throw new IllegalStateException("Utility class.");
    }

    private static Random random = new Random();
    public static String generateCardNumber() {
        Random random = new Random();

        StringBuilder cardNumberBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            cardNumberBuilder.append(digit);

            if ((i + 1) % 4 == 0 && i != 15) {
                cardNumberBuilder.append(" "); // Add space every 4 digits
            }
        }

        return cardNumberBuilder.toString();
    }

    public static String generateCardCvv() {
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }

        return cardNumber.toString();
    }

    public static String generateCardPin() {
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }

        return cardNumber.toString();
    }
}
