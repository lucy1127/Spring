package com.example.springhexpractice.config.exception;

import com.example.springhexpractice.domain.aggreate.valueObject.ErrorCode;
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
