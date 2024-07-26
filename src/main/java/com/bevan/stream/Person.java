package com.bevan.stream;

import lombok.Data;

/**
 * @author zbf
 * @since 2023/12/18 23:05
 */
@Data
public class Person {
    private int age;
    private String name;
    private int price;
    private String author;

    public Person() {
    }

    public Person(int age, String name, int price) {
        this.age = age;
        this.name = name;
        this.price = price;

    }
}
