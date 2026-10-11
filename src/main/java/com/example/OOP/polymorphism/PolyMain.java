package com.example.OOP.polymorphism;

import com.example.OOP.inheritance.InheritanceInterfaceMain;

public class PolyMain {

    public static void main(String[] args) {

    }

    // the class is build on abstraction, not implementation
    // any class which implements the interface can be passed there, as a
    // result we can extend the system without changing the logic
    private static class PerimeterProcessor {
        private Perimeter perimeterShape;

        public PerimeterProcessor(Perimeter perimeterShape) {
            this.perimeterShape = perimeterShape;
        }

        public void printPerimeter() {
            System.out.println(perimeterShape.perimeter());
        }
    }

    private interface Perimeter {
        double perimeter();
    }

    private static class Square implements Perimeter {
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

    private static class Rectangle implements Perimeter {
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
}
