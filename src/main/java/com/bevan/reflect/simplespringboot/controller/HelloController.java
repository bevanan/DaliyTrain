package com.bevan.reflect.simplespringboot.controller;

import com.bevan.reflect.simplespringboot.annotation.RequestMapping;
import com.bevan.reflect.simplespringboot.annotation.RestController;

/**
 * @author Baven
 * @since  2024/6/12 23:18
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public void hello() {
        System.out.println("hello world");
    }
}
