package com.bevan.ds;

public class BuilderTest {
    public static void main(String[] args) {
        RabbitMQClient newClient = new RabbitMQClient.Builder()
                .setHost("new-host")
                .setPort(5673)
                .build();
    }
}
