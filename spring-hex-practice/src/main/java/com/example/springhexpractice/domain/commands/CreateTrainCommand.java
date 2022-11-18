package com.example.springhexpractice.domain.commands;

import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainCommand {
    @JsonProperty("train_no")
    private Integer trainNo;
    @JsonProperty("train_kind")
    private String trainKind;
    private List<TrainStopRequest> stops;
}
