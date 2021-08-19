package com.example.rabbitProducer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SomeConfigRabbitMQ {
    @Value("${citro.test.order.client.canceling.queue}")
    private String orderProcTestQueue;
    @Value("${citro.test.order.client.canceling.exchange}")
    private String orderProcTestExchange;
    @Value("${citro.test.order.client.canceling.key}")
    private String orderProcTestKey;
    @Value("${citro.test.order.client.canceling.dead.letter.exchange}")
    private String orderProcTestExchange_DLQ_X;
    @Value("${citro.test.order.client.canceling.dead.letter.dead.letter.key}")
    private String orderProcTestKey_DLQ_K;
    @Value("${citro.test.order.client.canceling.dead.letter.queue}")
    private String orderProcTestQueue_DLQ;


    @Bean
    Queue testClientOrderCancelingQueue() {
        return QueueBuilder
                .durable(orderProcTestQueue)
                .withArgument("x-dead-letter-exchange", orderProcTestExchange_DLQ_X)
                .withArgument("x-dead-letter-routing-key", orderProcTestKey_DLQ_K)
                .build();
    }

    @Bean
    DirectExchange testClientOrderCancelingExchange() {
        return new DirectExchange(orderProcTestExchange);
    }

    @Bean
    Binding testClientOrderCancelingBinding() {
        return BindingBuilder.bind(testClientOrderCancelingQueue()).to(testClientOrderCancelingExchange()).with(orderProcTestKey);
    }

    @Bean
    Queue testClientOrderCancelingDlq() {
        return QueueBuilder
                .durable(orderProcTestQueue_DLQ)
                .withArgument("x-message-ttl", 600000)
                .build();
    }

    @Bean
    DirectExchange testClientOrderCancelingDeadLetterExchange() {
        return new DirectExchange(orderProcTestExchange_DLQ_X);
    }

    @Bean
    Binding testClientOrderCancelingDLQbindingPart1() {
        return BindingBuilder
                .bind(testClientOrderCancelingDlq())
                .to(testClientOrderCancelingDeadLetterExchange())
                .with(orderProcTestKey_DLQ_K);
    }

    @Bean
    Binding testClientOrderCancelingDLQbindingPart2() {
        return BindingBuilder
                .bind(testClientOrderCancelingDlq())
                .to(testClientOrderCancelingDeadLetterExchange())
                .with(orderProcTestQueue);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
