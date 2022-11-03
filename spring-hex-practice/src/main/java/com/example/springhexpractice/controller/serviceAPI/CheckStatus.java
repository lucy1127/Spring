package com.example.springhexpractice.controller.serviceAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckStatus {
    private int id;
    private Status category;
    private String name;
    private List<String> photoUrls;
    private List<Status> tags;
    private String status;
}
