package com.example.OOP.encapsulation;

import java.util.HashMap;
import java.util.Map;

public class EncapsulationMain {

    public static void main(String[] args) {
        final CoffeeMachine smallCoffeeMachine = new CoffeeMachine(20, 20, 20);
        var bigCoffeeMachine = new CoffeeMachine(40, 40, 40);


        smallCoffeeMachine.refillGrain(GrainType.ROBUST, 20);
        smallCoffeeMachine.refillGrain(GrainType.ARABICA, 20);
        smallCoffeeMachine.refillWater(20);
        smallCoffeeMachine.refillMilk(20);

        smallCoffeeMachine.makeCoffee(CoffeeSize.SMALL, GrainType.ARABICA);

        bigCoffeeMachine.refillGrain(GrainType.ROBUST, 40);
        bigCoffeeMachine.refillGrain(GrainType.ARABICA, 40);
        bigCoffeeMachine.refillWater(40);
        bigCoffeeMachine.refillMilk(40);

//        smallCoffeeMachine.extractSupplies(CoffeeSize.SMALL); works here because same class, but in general does not
//        work because private access

        var smallCoffee = smallCoffeeMachine.makeCoffee(CoffeeSize.SMALL, GrainType.ARABICA);
        var bigCoffee = bigCoffeeMachine.makeCoffee(CoffeeSize.LARGE, GrainType.ROBUST);

        smallCoffee.drink();
        bigCoffee.drink();

        System.out.println(smallCoffee.isEmpty());
        System.out.println(bigCoffee.isEmpty());

        var coffe2 = bigCoffeeMachine.makeCoffee(CoffeeSize.SMALL, GrainType.ROBUST);
        while (!coffe2.isEmpty()) {
            coffe2.sip();
            System.out.println("Sip is made");
        }

    }

    private enum GrainType {
        ROBUST, ARABICA
    }

    // enum holds constant values, which can also have internal properties like here
    private enum CoffeeSize {
        SMALL(10, 5, 2),
        MEDIUM(20, 10, 4),
        LARGE(30, 15, 6);

        private final double water;
        private final double milk;
        private final double coffee;

        CoffeeSize(double water, double milk, double coffee) {
            this.water = water;
            this.milk = milk;
            this.coffee = coffee;
        }
    }

    private static class Coffee {
        private static int coffeeCount = 0;

        private double milk;
        private double water;
        private double coffee;

        public Coffee(double milk, double water, double coffee) {
            coffeeCount++;
            this.milk = milk;
            this.water = water;
            this.coffee = coffee;
        }

        public boolean isEmpty() {
            return milk <= 0 && water <= 0 && coffee <= 0;
        }

        public void drink() {
            this.milk = 0;
            this.water = 0;
            this.coffee = 0;
        }

        public void sip() {
            this.milk -= 0.1;
            this.water -= 0.1;
            this.coffee -= 0.1;
        }
    }

    private static class CoffeeMachine {

        private double waterSupplies;
        private double milkSupplies;
        private final Map<GrainType, Double> grainSupplies;
        private double processedGain;

        // internal properties, never exposed, never get changed, the same way you cannot change physical
        // properties of already existing machine -> therefore final, set only once on create
        private final double maxWaterSupplies;
        private final double maxMilkSupplies;
        private final double maxGrainSupplyPerType;

        public CoffeeMachine(double maxWaterSupplies,
                             double maxMilkSupplies,
                             double maxGrainSupplyPerType) {
            this.maxWaterSupplies = maxWaterSupplies;
            this.maxMilkSupplies = maxMilkSupplies;
            this.maxGrainSupplyPerType = maxGrainSupplyPerType;
            this.grainSupplies = new HashMap<>();
        }

        // public method, user has access to refill, internally logic controls the business logic
        public void refillMilk(double volume) {
            if (volume < 0) {
                throw new RuntimeException("Value cannot be negative");
            }
            if (milkSupplies + volume > maxMilkSupplies) {
                throw new RuntimeException("Too much milk");
            }
            milkSupplies += volume;
        }

        // same
        public void refillWater(double volume) {
            if (volume < 0) {
                throw new RuntimeException("Value cannot be negative");
            }
            if (waterSupplies + volume > maxWaterSupplies) {
                throw new RuntimeException("Too much water");
            }
            waterSupplies += volume;
        }

        // same
        public void refillGrain(GrainType grainType, double volume) {
            if (volume < 0) {
                throw new RuntimeException("Value cannot be negative");
            }
            if (grainSupplies.getOrDefault(grainType, 0.0) + volume > maxGrainSupplyPerType) {
                throw new RuntimeException("Too much grain");
            }
            grainSupplies.compute(grainType, (k, v) -> v == null ? volume : v + volume);
        }

        // same
        public Coffee makeCoffee(CoffeeSize size, GrainType grainType) {
            validateSupplies(size, grainType);
            prepareCoffee(size, grainType);
            extractSupplies(size);
            return new Coffee(size.milk, size.water, size.coffee);
        }

        // private method, external user should not have access to this part, this is the internal business logic
        private void extractSupplies(CoffeeSize size) {
            this.processedGain -= size.coffee;
            this.milkSupplies -= size.milk;
            this.waterSupplies -= size.water;
        }

        // same
        private void prepareCoffee(CoffeeSize size, GrainType grainType) {
            grainSupplies.compute(grainType, (k, v) -> v - size.coffee);
            this.processedGain += size.coffee;
        }

        // same
        private void validateSupplies(CoffeeSize size, GrainType grainType) {
            if (size.milk > milkSupplies) {
                throw new RuntimeException("Not enough milk");
            }
            if (size.water > waterSupplies) {
                throw new RuntimeException("Not enough water");
            }
            if (size.coffee > grainSupplies.getOrDefault(grainType, 0.0)) {
                throw new RuntimeException("Not enough coffee");
            }
        }
    }
}

