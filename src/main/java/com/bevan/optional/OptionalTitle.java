package com.bevan.optional;

import com.bevan.optional.dao.Person;

import java.util.Optional;

/**
 * @author zbf
 * @since 2024/1/3 23:50
 */
public class OptionalTitle {

    public static void main(String[] args) {
        Person person = new Person();
        test(person);
    }

    public static void test(Person person) {
        if (person != null) {
            int age = person.getAge();
        } else {
            throw new RuntimeException("dfsfd");
        }
        // ||
        Integer i = Optional.ofNullable(person).map(Person::getAge).orElseThrow(RuntimeException::new);
    }
}
