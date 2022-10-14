package com.example.Spring3;

import com.example.Spring3.activeMQ.TopicConsumer;
import com.example.Spring3.activeMQ.TopicProducer;
import com.example.Spring3.controller.dto.response.DeleteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Spring1ApplicationTests {

	@Autowired
	private TopicProducer topicProducer;
	@Test
	void contextLoads() throws Exception {
		DeleteResponse deleteResponse=new DeleteResponse();
		deleteResponse.setMessage("Hello world");
		topicProducer.sendMsg(deleteResponse);
	}

}
