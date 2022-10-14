package com.example.Spring2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static com.example.Spring2.Server.writeOutput;

public class Client2 {
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
            throw new SocketException(e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
