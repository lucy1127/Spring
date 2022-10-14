package com.example.Spring3.activeMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @JmsListener(destination = "Spring3.topic")
    public void readActiveQueue(String msg) {
        System.out.println("Q1 : " + msg);
    }

    @JmsListener(destination = "Spring3.topic")
    public void readActiveQueue1(String msg) {
        System.out.println("Q2 : " + msg);
    }

}
