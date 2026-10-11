package com.example.OOP.polymorphism;

import java.util.Set;

public class PolyMain2 {

    public static void main(String[] args) {
        var processor = new PaymentProcessor();

        processor.processPayment(10, "USD", new CardPaymentMethod());
        processor.processPayment(10, "PLN", new CardPaymentMethod());
    }

    private static class PaymentProcessor {

        // method applies any payment method and uses it's contract to make decisions, as a
        // result we can use different payment method without changing the system
        public void processPayment(double amount, String currency, PaymentMethod paymentMethod) {
            if (!paymentMethod.supportedCurrencies().contains(currency)) {
                throw new UnsupportedOperationException("");
            }
            System.out.println("processed %s %s".formatted(amount,currency));
        }
    }

    private interface PaymentMethod {
        Set<String> supportedCurrencies();
    }

    private static class CardPaymentMethod implements PaymentMethod {
        @Override
        public Set<String> supportedCurrencies() {
            return Set.of("USD", "EUR");
        }
    }

    private static class CashPaymentMethod implements PaymentMethod {
        @Override
        public Set<String> supportedCurrencies() {
            return Set.of("USD", "GBP");
        }
    }
}
