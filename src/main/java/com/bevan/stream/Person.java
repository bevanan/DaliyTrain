package com.bevan.stream;

/**
 * @author zbf
 * @since 2023/12/18 23:05
 */
public class Person {
    private int age;
    private String name;
    private int price;
    private String author;

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

    private void secretMethod() {
        System.out.println("This is a secret method!");
    }

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Person(int age, String name, int price) {
        this.age = age;
        this.name = name;
        this.price = price;
    }

    public Person(int age, String name, int price, String author) {
        this.age = age;
        this.name = name;
        this.price = price;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                '}';
    }
}
