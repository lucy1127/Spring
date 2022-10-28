package com.example.Spring3.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
@EnableJms
public class SpringActiveMQConfig {

    private String brokerUrl = "tcp://localhost:61616"; //更改


    @Bean
    public Queue queue() {
        return new ActiveMQQueue("Spring3.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("Spring3.topic");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "failover:(tcp://localhost:61616)?initialReconnectDelay=1000&maxReconnectDelay=30000"); //更改
        //initialReconnectDelay：表示第一次嘗試重連之前等待的時間。 maxReconnectDelay：預設30000，單位毫秒，表示兩次重連之間的最大時間間隔。
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        //設定 訊息佇列的重發機制
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setInitialRedeliveryDelay(0); // 初始重發延遲時間
        queuePolicy.setRedeliveryDelay(1000);//重發延遲時間
        queuePolicy.setUseExponentialBackOff(false);
        queuePolicy.setMaximumRedeliveries(2);// 最大重传次数

        RedeliveryPolicy topicPolicy = new RedeliveryPolicy();
        topicPolicy.setInitialRedeliveryDelay(0);
        topicPolicy.setRedeliveryDelay(1000);
        topicPolicy.setUseExponentialBackOff(false);
        topicPolicy.setMaximumRedeliveries(3);

        RedeliveryPolicyMap map = activeMQConnectionFactory.getRedeliveryPolicyMap();
        map.put(new ActiveMQQueue("Spring3.queue"),queuePolicy);
        map.put(new ActiveMQTopic("Spring3.topic"),topicPolicy);

        activeMQConnectionFactory.setRedeliveryPolicyMap(map);

        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
    //JmsTemplate會自動為您建立Connection、Session、Message並進行傳送

}
