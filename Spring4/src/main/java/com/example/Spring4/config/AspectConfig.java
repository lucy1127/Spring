package com.example.Spring4.config;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectConfig {
    @Before("execution(* com.example.Spring4.service.MgniService.*(..))")
    private void printAspect(){
        System.out.println("Aspecting...");
    }
}
