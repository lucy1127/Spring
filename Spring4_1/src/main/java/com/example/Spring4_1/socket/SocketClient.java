package com.example.Spring4_1.socket;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketClient {
    private static Socket s;
    public static boolean connection_state = false;
    private static final Logger logger = Logger.getLogger(SocketClient.class);

    public static void sendMessage(String temp) throws Exception {
        s = null;

        try {
            s = new Socket("localhost", 5000);
//            connect(temp);

            writeOutput(temp, s);

            BufferedReader bu = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String rs;
            while ((rs = bu.readLine()) != null) {
                System.out.println(rs);
            }

            bu.close();
        } catch (SocketException e) {
            System.out.println("Wrong"); //斷線重連
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }

    }
//    private static void connect(String temp) {
//        try {
//            s = new Socket("localhost", 5000);
//            connection_state = true;
//            new Thread(new SocketClient_heart(s)).start();
//        } catch (SocketException e){
//            connection_state = false;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void reconnect() {
//        while (!connection_state) {
//            System.out.println("trying to reconnect");
//            connect("reconnect");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void writeOutput(String message, Socket s) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println(message);
        pr.flush();
    }

}