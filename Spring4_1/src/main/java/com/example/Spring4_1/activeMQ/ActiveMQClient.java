package com.example.Spring4_1.activeMQ;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;

public class ActiveMQClient {
    private static final Logger logger = Logger.getLogger(com.example.Spring4_1.Consumer.class);

    @JmsListener(destination = "Spring3.queue", containerFactory = "queueConnectionFactory")
    public void consumeMessage(String message) {
        logger.info("Message received from activemq queue\n" + message);
        System.out.println("============================================\n");
        System.out.println("Message received from activemq queue\n" + message);
    }
}
