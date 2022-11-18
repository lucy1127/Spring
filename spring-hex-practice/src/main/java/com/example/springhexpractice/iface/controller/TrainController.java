package com.example.springhexpractice.iface.controller;

import com.example.springhexpractice.domain.commands.CreateTrainCommand;
import com.example.springhexpractice.domain.commands.CreateTrainTicketCommand;
import com.example.springhexpractice.domain.aggreate.entity.Train;
import com.example.springhexpractice.domain.aggreate.entity.TrainTicket;
import com.example.springhexpractice.iface.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.iface.controller.dto.request.CreateTrainTicketRequest;
import com.example.springhexpractice.iface.controller.dto.response.CreateResponse;
import com.example.springhexpractice.iface.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.iface.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.config.exception.CheckErrorException;
import com.example.springhexpractice.service.application.TrainCommandService;
import com.example.springhexpractice.service.application.TrainQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    TrainQueryService queryService;
    @Autowired
    TrainCommandService commandService;

    ObjectMapper objectMapper = new ObjectMapper();


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

        CreateTrainCommand createTrainCommand = objectMapper.convertValue(request, CreateTrainCommand.class);

        Train result = commandService.createTrain(createTrainCommand);

        return objectMapper.convertValue(result.getUuid(), CreateResponse.class);
    }

    @PostMapping("/ticket")
    public CreateResponse createTicket(@Valid @RequestBody CreateTrainTicketRequest request) throws ParseException, CheckErrorException {

        CreateTrainTicketCommand createTrainTicketCommand = objectMapper.convertValue(request,CreateTrainTicketCommand.class);

        TrainTicket result = commandService.createTrainTicket(createTrainTicketCommand);

        return objectMapper.convertValue(result.getTicketNo(), CreateResponse.class);
    }


}
