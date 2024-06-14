package com.bevan.stream;

import lombok.Data;

/**
 * @author zbf
 * @since 2023/12/18 23:05
 */
@Data
public class Student implements People {
    private int age;
    private String name;
    private int price;
    private String author;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public void walk() {
        System.out.println("I can walk!!!");
    }
}
