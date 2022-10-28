package com.example.Spring4_1.response;


import com.example.Spring4_1.model.Cashi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashiResponse {
    private List<Cashi> cashiList;
    private String message;
}
