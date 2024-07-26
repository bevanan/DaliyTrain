package com.bevan.stream;

import lombok.Data;

/**
 * @author zbf
 * @since 2023/12/18 23:05
 */
@Data
public class Student implements People {
    private Integer age;
    private String name;
    private Integer price;
    private String author;

    public Student(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public void walk() {
        System.out.println("I can walk!!!");
    }
}
