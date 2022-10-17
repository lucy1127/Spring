package com.example.Spring3.activeMQ;

import com.example.Spring3.controller.MgniController;
import com.example.Spring3.controller.dto.request.CreateMgniRequest;
import com.example.Spring3.controller.dto.request.DeleteRequest;
import com.example.Spring3.controller.dto.response.DeleteResponse;
import com.example.Spring3.controller.dto.response.MgniResponse;
import com.example.Spring3.controller.dto.response.UpdateResponse;
import com.example.Spring3.controller.error.MgniNotFoundException;
import com.example.Spring3.model.entity.Mgni;
import com.example.Spring3.service.MgniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import javax.jms.Topic;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/produce")
public class QueueProducer{
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private MgniService mgniService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/getList")
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


    @PostMapping(value = "/create")
    public Mgni create(@Valid @RequestBody CreateMgniRequest request) {
        Mgni mgni = new Mgni();
        try {
            mgni = this.mgniService.createSettlementMargin(request);
            jmsTemplate(queue, json(mgni));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mgni;
    }

    @PostMapping(value = "/update")
    public UpdateResponse updateData(@Valid @RequestBody CreateMgniRequest request) {
        UpdateResponse updateResponse = new UpdateResponse();
        try {
            updateResponse.setMgni(this.mgniService.updateData(request));
            updateResponse.setMessage("Success");
            jmsTemplate(queue, json(updateResponse));
        } catch (MgniNotFoundException e) {
            return new UpdateResponse(null, "This id doesn't exist....");
        } catch (Exception e) {
            updateResponse.setMessage(e.getMessage());
        }
        return updateResponse;
    }

    @DeleteMapping(value = "/delete")
    public DeleteResponse deleteData(@Valid @RequestBody DeleteRequest request) {
        DeleteResponse deleteResponse = new DeleteResponse();
        try {
            this.mgniService.deleteData(request);
            deleteResponse.setMessage("Success");
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