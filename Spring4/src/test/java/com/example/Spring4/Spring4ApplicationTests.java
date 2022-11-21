package com.example.Spring4;

import com.example.Spring4.activeMQ.TopicProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Spring4ApplicationTests {

	@Autowired
	private TopicProducer topicProducer;

	@Test
	void contextLoads() throws Exception {
		topicProducer.sendMsg();
	}

}
