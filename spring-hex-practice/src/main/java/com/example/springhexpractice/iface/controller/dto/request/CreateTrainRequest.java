package com.example.springhexpractice.iface.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainRequest {

    @NotNull(message = "車次不可為空")
    @JsonProperty("train_no")
    private Integer trainNo; //車次

    @NotEmpty(message = "車種不可為空")
    @JsonProperty("train_kind")
    private String trainKind; //車種

    @Valid
    @NotEmpty(message = "停靠站不可為空")
    private List<TrainStopRequest> stops;

}
