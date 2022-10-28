package com.example.Spring4_1.response;


import com.example.Spring4_1.model.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponse {
    private Mgni mgni;
    private String message;
}
