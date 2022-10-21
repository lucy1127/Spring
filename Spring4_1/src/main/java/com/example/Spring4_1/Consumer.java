package com.example.Spring4_1;

import com.example.Spring4_1.socket.SocketClient;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer implements CommandLineRunner {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your request : ");
            String type = scanner.nextLine();
            String requestBody = "";

            if (type.equals("Socket Connect")) {
                System.out.println("Socket request : ");
                requestBody = scanner.nextLine();
                SocketClient.sendMessage(requestBody);
            } else if (type.equals("ActiveMQ Connect")) {
                System.out.println("ActiveMQ request : ");
                requestBody = scanner.nextLine();
                jmsTemplate.convertAndSend(new ActiveMQQueue("request.queue"), requestBody);
            } else if (type.equals("exit")) {
                break;
            } else {
                System.out.println("不要亂打");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("stop...");
    }


}