package com.example.springhexpractice.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"trainNo","trainKind"})
public class SelectStopResponse {

    @JsonProperty("train_no")
    private Integer trainNo;
    @JsonProperty("train_kind")
    private String trainKind;
}
