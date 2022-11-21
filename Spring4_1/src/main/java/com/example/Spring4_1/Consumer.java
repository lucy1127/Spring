package com.example.Spring4_1;

//import com.example.Spring4_1.config.OpenExe;

import com.example.Spring4_1.restful.RestClient;
import com.example.Spring4_1.socket.SocketClient;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Scanner;

@Component
public class Consumer implements CommandLineRunner {
    @Autowired
    private JmsTemplate jmsTemplate;

    private static String requestBody = "";

    @Override
    public void run(String... args) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        Scanner scanner = new Scanner(System.in);
        Runtime rt = Runtime.getRuntime();

        while (true) {
            String uid = java.util.UUID.randomUUID().toString();

            System.out.println("Please enter your request (socket / MQ / restful): ");
            String type = scanner.nextLine().toLowerCase();

            if (type.equals("socket")) {
                System.out.println("Socket request : ");
                requestBody = scanner.nextLine();
                SocketClient.sendMessage(requestBody);
            } else if (type.equals("mq")) {
//                OpenExe.runMQ("open");
                System.out.println("ActiveMQ request : ");
                requestBody = scanner.nextLine();
                jmsTemplate.send(new ActiveMQQueue("request.queue"), messageCreator -> {
                    TextMessage message = messageCreator.createTextMessage();
                    message.setText(requestBody);
                    message.setStringProperty("uid", uid);
                    return message;
                });

                Message jmsMessage = jmsTemplate.receiveSelected("Spring3.queue", "uid='" + uid + "'");
                if (jmsMessage == null) {
                    System.out.println("receive time out ");
                } else {
                    TextMessage textMsg = (TextMessage) jmsMessage;
                    System.out.println("==============================================");
                    System.out.println("uid = " + jmsMessage.getStringProperty("uid"));
                    System.out.println("==============================================");
                    System.out.println("Response msg : \n" + textMsg.getText());
                    System.out.println("==============================================");
                }
            } else if (type.equals("restful")) {
                System.out.println("RestFul request : ");
                requestBody = scanner.nextLine();
                RestClient.sendRestFulMessage(requestBody);
            } else if (type.equals("exit")) {
//                OpenExe.runMQ("exit");
                break;
            } else {
                System.out.println("Try again...");
            }

        }
        System.out.println("stop...");
    }


}

