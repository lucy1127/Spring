package com.example.Spring3_1.config;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
@EnableJms
public class SpringActiveMQConfig {

    private String brokerUrl = "tcp://localhost:61616";

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
                "failover:(tcp://localhost:61616)?initialReconnectDelay=1000&maxReconnectDelay=30000");
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

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
//    ActiveMQ提供failover機制去實現斷線重連的高可用性，可以使得連接斷開之後，不斷的重試連接到一個或多個brokerURL。



    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }

}