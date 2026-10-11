package com.example.OOP.inheritance;

import java.util.List;

public class InheritanceInterfaceMain {

    public static void main(String[] args) {
        // if we change type to Shape, we still can add items, but forEach will break
        List<Perimeter> shapes = List.of(new Rectangle(1,2), new Square(3,4), new Triangle(3, 4, 5));
        shapes.forEach(it -> it.perimeter()); // all items follow the same interface contract, can be done
    }

    private interface Perimeter {
        double perimeter();

        default void test() {
           throw new RuntimeException("Unsupported");
        }
    }

    private static abstract class Shape {

    }

    private static class Square extends Shape implements Perimeter {
        private double h;
        private double w;

        public Square(double h, double w) {
            this.h = h;
            this.w = w;
        }

        @Override
        public double perimeter() {
            return h + h + w + w;
        }
    }

    private static class Rectangle extends Shape implements Perimeter {
        private double h;
        private double w;

        public Rectangle(double h, double w) {
            this.h = h;
            this.w = w;
        }

        @Override
        public double perimeter() {
            return h + h + w + w;
        }
    }

    private static class Triangle extends Shape implements Perimeter {
        private double side1;
        private double side2;
        private double side3;

        public Triangle(double side1, double side2, double side3) {
            validateSides(side1, side2, side3);
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
        }

        private void validateSides(double side1, double side2, double side3) {
            if (side1 + side2 < side3 || side1 + side3 < side2 || side2 + side3 < side1) {
                throw new RuntimeException("Invalid triangle");
            }
        }

        @Override
        public double perimeter() {
            return side1 + side2 + side3;
        }
    }
}
