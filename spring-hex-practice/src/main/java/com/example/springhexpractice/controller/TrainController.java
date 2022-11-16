package com.example.springhexpractice.controller;

import com.example.springhexpractice.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.controller.dto.request.CreateTrainTicketRequest;
import com.example.springhexpractice.controller.dto.response.CreateResponse;
import com.example.springhexpractice.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.service.applicationLayer.CommandTrainService;
import com.example.springhexpractice.service.applicationLayer.QueryTrainService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
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
    public SelectTrainResponse trainStopAssociation(@PathVariable Integer train_no) {
        return queryService.selectTrain(train_no);
    }

    @GetMapping("/train")
    public List<SelectStopResponse> selectStop(@RequestParam(required = false) String via) throws CheckErrorException {
        return queryService.selectStop(via);
    }

    @PostMapping("/train")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateResponse createTrain(@RequestBody CreateTrainRequest request) throws CheckErrorException {

        return new CreateResponse(commandService.createTrain(request));
    }

    @PostMapping("/ticket")
    public CreateResponse createTicket(@RequestBody CreateTrainTicketRequest request) throws ParseException, CheckErrorException {
        return new CreateResponse(commandService.createTrainTicket(request));
    }


}
