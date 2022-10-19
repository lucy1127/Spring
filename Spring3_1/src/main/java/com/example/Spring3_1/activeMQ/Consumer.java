package com.example.Spring3_1.activeMQ;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer extends Thread{

    private static final Logger logger = Logger.getLogger(Consumer.class);

    @JmsListener(destination = "Spring3.queue", containerFactory = "queueConnectionFactory")
    public void consumeMessage(String message) {
        logger.info("Message received from activemq queue\n" + message);
        System.out.println("============================================\n");
        System.out.println("Message received from activemq queue\n" + message);
    }

    @JmsListener(destination = "Spring3.topic", containerFactory = "topicConnectionFactory")
    public void readActiveQueue(String msg) {
        logger.info("Message received from activemq topic\n" + msg);
        System.out.println("Q : " + msg);
    }



}