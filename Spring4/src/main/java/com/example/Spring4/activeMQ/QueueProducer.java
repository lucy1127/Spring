package com.example.Spring4.activeMQ;

import com.example.Spring4.controller.dto.request.CreateMgniRequest;
import com.example.Spring4.controller.dto.request.DeleteRequest;
import com.example.Spring4.controller.dto.request.Request;
import com.example.Spring4.controller.dto.response.DeleteResponse;
import com.example.Spring4.controller.dto.response.MgniResponse;
import com.example.Spring4.controller.dto.response.UpdateResponse;
import com.example.Spring4.controller.error.ErrorDataException;
import com.example.Spring4.controller.error.MgniNotFoundException;
import com.example.Spring4.model.entity.Mgni;
import com.example.Spring4.service.MgniService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Reader;


@Component
public class QueueProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private MgniService mgniService;
    private static final ObjectMapper mapper = new ObjectMapper();
    private String temp = "";

    @JmsListener(destination = "request.queue", containerFactory = "queueConnectionFactory")
    public void receiveQueue(final Message msg) throws JsonProcessingException, JMSException,Exception {
        try {
            mapper.findAndRegisterModules();

            String messageData = null;
            System.out.println("==============================================");
            System.out.println("uid = " + msg.getStringProperty("uid"));
            System.out.println("==============================================");
            String uid = msg.getStringProperty("uid");

            TextMessage textMessage = (TextMessage) msg;
            messageData = textMessage.getText();

            System.out.println("Message: " + messageData);
            System.out.println("==============================================");
            Request request = new Gson().fromJson(messageData, Request.class);



            switch (request.getType().toLowerCase()) {
                case "select":
                    temp = mapper.writeValueAsString(getAllData(uid));
                    break;
                case "create":
                    temp = mapper.writeValueAsString(create(request.getRequest(), uid));
                    break;
                case "update":
                    temp = mapper.writeValueAsString(updateData(request.getRequest(), uid));
                    break;
                case "delete":
                    temp = mapper.writeValueAsString(deleteData(request.getRequest().getId(), uid));
                    break;
                default:
//                    temp = mapper.writeValueAsString("Wrong Type");
                    jmsTemplate(queue,"Wrong Type",uid);
                    break;
            }
        }catch (Exception e) {
            jmsTemplate(queue,"Wrong Data",msg.getStringProperty("uid"));
        }
    }


    public MgniResponse getAllData(String u) {
        MgniResponse mgniResponse = new MgniResponse();
        try {
            mgniResponse.setMgniList(this.mgniService.getData());
            mgniResponse.setMessage("Success");
            jmsTemplate(queue, json(mgniResponse),u);
        } catch (Exception e) {
            mgniResponse.setMessage(e.getMessage());
        }
        return mgniResponse;
    }


    public Mgni create(CreateMgniRequest request,String u) throws Exception {
        Mgni mgni = new Mgni();
        try {
            mgni = this.mgniService.createSettlementMargin(request);
            jmsTemplate(queue, json(mgni),u);
        } catch (ErrorDataException e) {
            jmsTemplate(queue, json(e.getErrorMessage()),u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mgni;
    }

    public UpdateResponse updateData(CreateMgniRequest request,String u) throws Exception {
        UpdateResponse updateResponse = new UpdateResponse();
        try {
            updateResponse.setMgni(this.mgniService.updateData(request));
            updateResponse.setMessage("Success");
            jmsTemplate(queue, json(updateResponse),u);
        } catch (ErrorDataException e) {
            jmsTemplate(queue, json(e.getErrorMessage()),u);
            return new UpdateResponse(null, "This id doesn't exist....");
        } catch (Exception e) {
            updateResponse.setMessage(e.getMessage());
        }
        return updateResponse;
    }

    public DeleteResponse deleteData(String request,String u) throws Exception {
        DeleteResponse deleteResponse = new DeleteResponse();
        try {
            this.mgniService.deleteData(request);
            deleteResponse.setMessage("Success");
            jmsTemplate(queue, json(deleteResponse),u);
        } catch (MgniNotFoundException e) {
            jmsTemplate(queue, json(e.getErrorMessage()),u);
            return new DeleteResponse("This id doesn't exist....");
        } catch (Exception e) {
            deleteResponse.setMessage(e.getMessage());
        }
        return deleteResponse;
    }

    private static String json(Object object) throws Exception {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    private void jmsTemplate(Queue queue, String j,String uid) {
        jmsTemplate.send(queue, messageCreator -> {
            TextMessage message = messageCreator.createTextMessage();
            message.setText(j);
            message.setStringProperty("uid", uid);
            return message;
        });
    }

}