package com.lambda.demo.test;

interface B {
    int add(int a, int b);
}

public class ModernJavaTest {
    public static void main(String args[]) {
        B b = (x,y) -> x + y;
        System.out.println("add(1,2) = " + b.add(1, 2));
    }
}