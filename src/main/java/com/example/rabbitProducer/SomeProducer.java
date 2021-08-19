package com.example.rabbitProducer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SomeProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${citro.test.order.client.canceling.exchange}")
    private String orderProcTestExchange;

    @Value("${citro.test.order.client.canceling.key}")
    private String orderProcTestKey;
    public void CheckProducer(String message){
        amqpTemplate.convertAndSend(orderProcTestExchange, orderProcTestKey, message);
    };
}
