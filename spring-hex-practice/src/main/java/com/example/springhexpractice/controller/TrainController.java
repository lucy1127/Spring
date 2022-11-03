package com.example.springhexpractice.controller;

import com.example.springhexpractice.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.controller.dto.request.CreateTrainTicket;
import com.example.springhexpractice.controller.dto.response.CreateResponse;
import com.example.springhexpractice.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.controller.error.ErrorMessage;
import com.example.springhexpractice.controller.error.TrainNotFoundException;
import com.example.springhexpractice.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.text.ParseException;
import java.util.List;


@Validated
@RestController
//@RequestMapping("/train")
public class TrainController {

    @Autowired
    TrainService trainService;

    @GetMapping("/train/{train_no}/stops")
    public SelectTrainResponse selectTrain(@Min(value = 0, message = "車次必須為正整數") @PathVariable Integer train_no) {
        return trainService.selectTrain(train_no);
    }

    @GetMapping("/train")
    public List<SelectStopResponse> selectStop(@NotEmpty(message = "Required String parameter 'via' is not present") @RequestParam String via) {
        return trainService.selectStop(via);
    }

    @PostMapping("/train")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateResponse createTrain(@Valid @RequestBody CreateTrainRequest request) throws ParseException {
        return trainService.createTrain(request);
    }

    @PostMapping("/ticket")
    public CreateResponse createTicket(@Valid @RequestBody CreateTrainTicket request) throws ParseException {
        return trainService.createTrainTicket(request);
    }


}
