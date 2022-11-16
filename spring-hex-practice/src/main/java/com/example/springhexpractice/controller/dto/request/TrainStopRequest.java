package com.example.springhexpractice.controller.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainStopRequest {

//    @Length(max = 20, message = "The string length of name must be no more than 20")
    @JsonProperty("stop_name")
    private String stopName;

//    @Pattern(regexp = "([01]\\d|2?[0-3]):([0-5][0-9])$", message = "時間格式錯誤")
    @JsonProperty("stop_time")
    private String stopTime;
}
