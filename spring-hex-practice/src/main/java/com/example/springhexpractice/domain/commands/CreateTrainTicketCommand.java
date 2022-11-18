package com.example.springhexpractice.domain.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainTicketCommand {
    @JsonProperty("train_no")
    private Integer trainNo;
    @JsonProperty("from_stop")
    private String fromStop;
    @JsonProperty("to_stop")
    private String toStop;
    @JsonProperty("take_date")
    private String takeDate;
}
