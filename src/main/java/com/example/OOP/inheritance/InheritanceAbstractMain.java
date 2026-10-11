package com.example.OOP.inheritance;

import java.util.List;

public class InheritanceAbstractMain {

    public static void main(String[] args) {
        List<Animal> animals = List.of(new Cat("Cat"), new Dog("Dog"));
        animals.forEach(it -> it.voice());
//        animals.forEach(it -> it.bark()); does not work, because animals does not have bark method in contract

        List<Dog> dogs = List.of(new Dog("Dog 1"), new Dog("Dog 2"));
        dogs.forEach(it -> it.bark()); // now collection can only hold dogs, which mean that they can bark
    }

    // animal is abstract thing, therefore class is abstract, but it can also have state, so we use a class here
    private static abstract class Animal {
        private String name;

        public Animal(String name) {
            this.name = name;
        }

        public void sayName() {
            System.out.println("My name is %s".formatted(name));
        }

        // all children will have vois, this is a part of a contract
        public abstract void voice();

        public void defaultMethod() {
            System.out.println("AAA");
        }
    }

    private static class Cat extends Animal {

        public Cat(String name) {
            super(name);
        }

        @Override
        public void voice() {
            System.out.println("Meow");
        }
    }

    private static class Dog extends Animal {

        public Dog(String name) {
            super(name);
        }

        @Override
        public void voice() {
            System.out.println("Woof");
        }

        // only dog can bark
        public void bark() {
            System.out.println("Bark");
        }
    }
}
