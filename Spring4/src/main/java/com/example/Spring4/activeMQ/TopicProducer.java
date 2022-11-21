package com.example.Spring4.activeMQ;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Topic;

@RestController
@RequestMapping("/TopicProduce")
public class TopicProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;


    @GetMapping("/send")
    public void sendMsg() throws Exception { //test
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString("Hello world");

        jmsMessagingTemplate.convertAndSend(topic, json);
    }

}


