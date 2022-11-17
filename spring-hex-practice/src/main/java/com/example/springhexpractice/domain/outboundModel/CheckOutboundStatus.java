package com.example.springhexpractice.domain.outboundModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutboundStatus {
    private int id;
    private OutboundStatus category;
    private String name;
    private List<String> photoUrls;
    private List<OutboundStatus> tags;
    private String status;
}
