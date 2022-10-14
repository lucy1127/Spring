package com.example.Spring3.activeMQ;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class QueueConsumer {

    private static final Logger logger = Logger.getLogger(QueueConsumer.class);

    @JmsListener(destination = "Spring3.queue")
    public void consumeMessage(String message) {
         logger.info("Message received from activemq queue---\n"+message);
        System.out.println("============================================\n");
        System.out.println("Message received from activemq queue---\n" + message);
    }
}
