package com.example.Spring2;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import static com.example.Spring2.Server.writeOutput;

public class Client extends Thread {
    public static void main(String[] args) throws Exception {
        boolean close = false;
        try {
            while (!close) {
                System.out.println("Write your request : ");
                Scanner scanner = new Scanner(System.in);
                String temp = scanner.nextLine();
                Socket s = new Socket("localhost", 5000);

                writeOutput(temp, s);

                BufferedReader bu = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String rs;
                if ((rs = bu.readLine()) != null) {
                    System.out.println(rs);
                } else {
                    close = true;
                }
                s.close();
            }
            System.out.println("Socket is closed");
        } catch (SocketException e) {
            throw new SocketException(e.getMessage()); //斷線重連
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
