package com.example.springhexpractice.iface.controller;

import com.example.springhexpractice.iface.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.iface.controller.dto.request.CreateTrainTicketRequest;
import com.example.springhexpractice.iface.controller.dto.response.CreateResponse;
import com.example.springhexpractice.iface.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.iface.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.service.applicationService.CommandTrainService;
import com.example.springhexpractice.service.applicationService.QueryTrainService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


@Validated
@RestController
public class TrainController {

    @Autowired
    QueryTrainService queryService;
    @Autowired
    CommandTrainService commandService;


    @GetMapping("/train/{train_no}/trainStopAssociation")
    public SelectTrainResponse trainStopAssociation(@Valid @PathVariable Integer train_no) {
        return queryService.selectTrain(train_no);
    }

    @GetMapping("/train")
    public List<SelectStopResponse> selectStop(@RequestParam String via) throws CheckErrorException {
        return queryService.selectStop(via);
    }

    @PostMapping("/train")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateResponse createTrain(@Valid @RequestBody CreateTrainRequest request) throws CheckErrorException {

        return new CreateResponse(commandService.createTrain(request));
    }

    @PostMapping("/ticket")
    public CreateResponse createTicket(@Valid @RequestBody CreateTrainTicketRequest request) throws ParseException, CheckErrorException {
        return new CreateResponse(commandService.createTrainTicket(request));
    }


}
