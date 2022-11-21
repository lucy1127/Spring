package com.example.Spring3;

import com.example.Spring3.activeMQ.TopicProducer;
import com.example.Spring3.controller.dto.response.DeleteResponse;
import com.example.Spring3.controller.dto.response.MessageResponse;
import org.apache.activemq.ScheduledMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.Message;
import javax.jms.TextMessage;

@SpringBootTest
class Spring1ApplicationTests {

	@Autowired
	private TopicProducer topicProducer;

	@Test
	void contextLoads() throws Exception {
		topicProducer.sendMsg();
	}

}
