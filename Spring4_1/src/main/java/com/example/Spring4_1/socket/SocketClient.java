package com.example.Spring4_1.socket;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class SocketClient {

    private static final Logger logger = Logger.getLogger(SocketClient.class);

    public static void sendMessage(String temp) throws Exception {

        try {

            Socket s = new Socket("localhost", 5000);

            writeOutput(temp, s);

            BufferedReader bu = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String rs;
            while ((rs = bu.readLine()) != null) {
                System.out.println(rs);
            }

            s.close();

            System.out.println("Socket is closed");
        } catch (SocketException e) {
            throw new SocketException(e.getMessage()); //斷線重連
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    public static void writeOutput(String message, Socket s) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println(message);
        pr.flush();
    }

}