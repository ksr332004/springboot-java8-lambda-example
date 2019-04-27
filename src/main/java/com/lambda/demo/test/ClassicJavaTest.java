package com.lambda.demo.test;


interface A {
    int add(int a, int b);
}

public class ClassicJavaTest {
    public static void main(String args[]) {
        A a = new A(){
            public int add(int a, int b) {
                return a+b;
            }
        };
        System.out.println("add(1,2) = " + a.add(1, 2));
    }
}