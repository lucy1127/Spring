//package com.example.Spring4_1.config;
//
//import java.util.Scanner;
//
//
//public class OpenExe {
//
//    public static void runMQ(String msg) throws Exception {
//
//        Runtime rt = Runtime.getRuntime();
//        try {
//            if (msg.equals("open")) {
//                //執行的檔案的位置
//                rt.exec("C:\\Program1\\Spring\\apache-activemq-5.17.2\\bin\\win64\\activemq.bat");
//                System.out.println("Open MQ！");
//            } else {
//                rt.exec("taskkill /f /im wrapper.exe");
//            }
//        } catch (Exception e) {
//            System.out.println("MQ close！");
//            e.printStackTrace();
//        }
//
//
//    }
//}
//
