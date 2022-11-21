package com.example.Spring4;

//import com.example.Spring4.config.OpenExe;
import com.example.Spring4.config.OpenExe;
import com.example.Spring4.socket.ClientHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.example.Spring4.*")
public class Spring4Application  {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Spring4Application.class, args);
        ServerSocket server = null;
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new OpenExe());
        try {
            server = new ServerSocket(5000);
            server.setReuseAddress(true);

            while (true) {
                Socket socket = server.accept();
                executorService.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    System.out.println("server 斷線");
                }
            }
            executorService.shutdown();
        }
    }

}
