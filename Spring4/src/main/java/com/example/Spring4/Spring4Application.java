package com.example.Spring4;

import com.example.Spring4.socket.ClientHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@ComponentScan(basePackages = "com.example.Spring4.*")
public class Spring4Application {
	public static void main(String[] args) {
		SpringApplication.run(Spring4Application.class, args);
		ServerSocket server = null;
		try {
			server = new ServerSocket(5000);
			server.setReuseAddress(true);
			ExecutorService executorService= Executors.newCachedThreadPool();
			while(true){
				Socket socket = server.accept();
//                Server client = new Server(socket);

				executorService.execute( new ClientHandler(socket));
//                new Thread(client).start();
			}

		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
