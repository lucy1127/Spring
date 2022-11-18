package com.example.springhexpractice.iface.controller.dto.response;

import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"trainNo","trainKind","stops"})
public class SelectTrainResponse {
    @JsonProperty("train_no")
    private Integer trainNo; //車次
    @JsonProperty("train_kind")
    private String trainKind; //車種

    private List<TrainStopRequest> stops;

}
