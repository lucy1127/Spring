package com.example.Spring3.activeMQ;


import com.example.Spring3.controller.dto.request.CreateMgniRequest;
import com.example.Spring3.controller.dto.request.Request;
import com.example.Spring3.controller.dto.response.DeleteResponse;
import com.example.Spring3.controller.dto.response.MgniResponse;
import com.example.Spring3.controller.dto.response.UpdateResponse;
import com.example.Spring3.controller.error.MgniNotFoundException;
import com.example.Spring3.model.entity.Mgni;
import com.example.Spring3.service.MgniService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;


@Validated
@RestController
public class QueueProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private MgniService mgniService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "request.queue", containerFactory = "queueConnectionFactory")
//    @SendTo({"response.queue"})
    public void receiveQueue(String msg) throws JsonProcessingException{
        mapper.findAndRegisterModules();
        System.out.println("Message: " + msg);
        Request request = new Gson().fromJson(msg, Request.class);

        String temp = "";

        switch (request.getType()) {
            case "selectMgni":
                temp = mapper.writeValueAsString(getAllData());
                break;
            case "create":
                temp = mapper.writeValueAsString(create(request.getRequestList()));
                break;
            case "update":
                temp = mapper.writeValueAsString(updateData(request.getRequestList()));
                break;
            case "delete":
                temp = mapper.writeValueAsString(deleteData(request.getRequestList().getId()));
                break;
            default:
                break;
        }
    }

    public MgniResponse getAllData() {
        MgniResponse mgniResponse = new MgniResponse();
        try {
            mgniResponse.setMgniList(this.mgniService.getData());
            mgniResponse.setMessage("Success");
            jmsTemplate(queue, json(mgniResponse));
        } catch (Exception e) {
            mgniResponse.setMessage(e.getMessage());
        }
        return mgniResponse;
    }


    public Mgni create(CreateMgniRequest request) {
        Mgni mgni = new Mgni();
        try {
            mgni = this.mgniService.createSettlementMargin(request);
            jmsTemplate(queue, json(mgni));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mgni;
    }

    public UpdateResponse updateData(CreateMgniRequest request) {
        UpdateResponse updateResponse = new UpdateResponse();
        try {
            updateResponse.setMgni(this.mgniService.updateData(request));
            updateResponse.setMessage("Update Success");
            jmsTemplate(queue, json(updateResponse));
        } catch (MgniNotFoundException e) {
            return new UpdateResponse(null, "This id doesn't exist....");
        } catch (Exception e) {
            updateResponse.setMessage(e.getMessage());
        }
        return updateResponse;
    }

    public DeleteResponse deleteData(String request) {
        DeleteResponse deleteResponse = new DeleteResponse();
        try {
            this.mgniService.deleteData(request);
            deleteResponse.setMessage("Delete Success");
            jmsTemplate(queue, json(deleteResponse));
        } catch (MgniNotFoundException e) {
            return new DeleteResponse("This id doesn't exist....");
        } catch (Exception e) {
            deleteResponse.setMessage(e.getMessage());
        }
        return deleteResponse;
    }


    private static String json(Object object) throws Exception {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    private void jmsTemplate(Queue queue, String j) {
        jmsTemplate.convertAndSend(queue, j);
    }

}