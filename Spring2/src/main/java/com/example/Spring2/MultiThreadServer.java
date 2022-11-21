package com.example.Spring2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(5000);
            server.setReuseAddress(true);
            ExecutorService executorService= Executors.newCachedThreadPool();
            while(true){
                Socket socket = server.accept();
//                Server client = new Server(socket);

                executorService.execute( new Server(socket));
//                new Thread(client).start();
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
