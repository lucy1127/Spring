package com.example.springhexpractice.iface.controller.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainStopRequest {
    @NotEmpty
    @JsonProperty("stop_name")
    private String stopName;

    @JsonProperty("stop_time")
    private String stopTime;
}
