package main.java.com.bevan.optional;

import com.bevan.optional.dao.Person;

import java.util.Optional;

/**
 * @author zbf
 * @since 2024/1/3 23:50
 */
public class OptionalTitle {

    public static void main(String[] args) {
        Person person = new Person();
        Optional.ofNullable(person).orElseThrow(RuntimeException::new);
    }
}
