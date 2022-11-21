package com.example.Spring4.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;


public class OpenExe extends Thread{

    @Override
    public  void run() {

        Runtime rt = Runtime.getRuntime();
        try {
            //執行的檔案的位置
            rt.exec("C:\\Program1\\Spring\\apache-activemq-5.17.2\\bin\\win64\\activemq.bat");
            System.out.println("Open MQ！");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String s = scanner.nextLine().toLowerCase();
                if (s.equals("close")) {
                    rt.exec("taskkill /f /im wrapper.exe");
                    System.exit(0);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("MQ close！");
            e.printStackTrace();
        }

    }
}

