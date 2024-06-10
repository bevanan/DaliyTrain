package com.bevan.optional.dao;

/**
 * @author zbf
 * @since 2023/12/18 23:05
 */
public class Person {
    private int age;
    private String name;
    private int price;
    private String author;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
