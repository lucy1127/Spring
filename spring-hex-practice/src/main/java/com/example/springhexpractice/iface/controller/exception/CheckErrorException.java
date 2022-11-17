package com.example.springhexpractice.iface.controller.exception;

import com.example.springhexpractice.domain.aggreate.util.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CheckErrorException extends Exception{
    private final List<ErrorCode> checkError = new ArrayList<>();

    public CheckErrorException(ErrorCode error){
            this.checkError.add(error);
    }
    public boolean hasErrors(){
        return !this.checkError.isEmpty();
    }

}
