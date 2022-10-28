package com.example.Spring3_1.activeMQ;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer implements CommandLineRunner {

    @Autowired
    private JmsTemplate jmsTemplate;
    private static final Logger logger = Logger.getLogger(Consumer.class);


    @JmsListener(destination = "Spring3.queue", containerFactory = "queueConnectionFactory")
    public void consumeMessage(String message) {
//        logger.info("Message received from activemq queue\n" + message);
        System.out.println("============================================\n");
        System.out.println("Message received from activemq queue\n" + message);
    }

//    @JmsListener(destination = "Spring3.topic", containerFactory = "topicConnectionFactory")
//    public void readActiveQueue(String msg) {
//        logger.info("Message received from activemq topic\n" + msg);
//        System.out.println("Q : " + msg);
//    }

    @Override
    public void run(String... args) throws Exception {
        boolean close = false;
        while (!close) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Request : ");
            String message = scanner.nextLine();
            if (message.equals("exit")) {
                close = true;
            }
            jmsTemplate.convertAndSend(new ActiveMQQueue("request.queue"), message);
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("stop...");
    }
}