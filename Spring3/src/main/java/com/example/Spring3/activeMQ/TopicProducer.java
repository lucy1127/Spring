package com.example.Spring3.activeMQ;


import com.example.Spring3.controller.dto.response.DeleteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Topic;

@RestController
@RequestMapping("/TopicProduce")
public class TopicProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    @PostMapping(value = "/sendMsg")
    public void sendMsg(@RequestBody DeleteResponse msg) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage(msg.getMessage());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(deleteResponse);
        jmsMessagingTemplate.convertAndSend(topic, json);
    }



}
