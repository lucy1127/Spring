package com.example.Spring4.socket;

import com.example.Spring4.config.SpringUtil;
import com.example.Spring4.controller.dto.request.Request;
import com.example.Spring4.service.MgniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.log4j.Logger;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class ClientHandler extends Thread{


    private MgniService mgniService = SpringUtil.getBean(MgniService.class);

    private static final Logger logger = Logger.getLogger(ClientHandler.class);

    private final Socket s;

    private static final ObjectMapper mapper = new ObjectMapper();

    public ClientHandler(Socket socket) {
        this.s = socket;
    }

    @Override
    public void run() {
        try {

            System.out.println("Client Connected");

            BufferedReader bu = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = bu.readLine(); //close
            Request request = new Gson().fromJson(str, Request.class);

            String temp = "";

            switch (request.getType()) {
                case "selectMgni":
                    temp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mgniService.getData());
                    break;
                case "create":
                    temp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mgniService.createSettlementMargin(request.getRequestList()));
                    break;
                case "update":
                    temp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mgniService.updateData(request.getRequestList()));
                    break;
                case "delete":
                    temp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mgniService.deleteData(request.getRequestList().getId()));
                    break;
                default:
                    break;
            }

            temp = "{\"message\":\"" + temp + "\"}";
            writeOutput(temp, s);

            s.close();
        } catch (SocketException e) {
            logger.error("Error Message: ", new SocketException("Socket is closed"));
        } catch (NullPointerException e) {
            logger.error("Error Message: ", new NullPointerException(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error Message: ", new Exception("Internal server problem"));
        }
    }

    public static void writeOutput(String message, Socket s) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println(message);
        pr.flush();
    }
}
