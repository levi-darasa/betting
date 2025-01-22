package com.betting.transaction.processor.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Value("${rabbitmq.consumer.prefetch}")
    private Integer prefectCount;

    @Qualifier("mqAskExecutor")
    private Executor mqAskExecutor;

    @Value("${rabbit.exchanges.balance-update}")
    private String balanceUpdateExchange;

    @Value("${rabbit.queues.balance-update}")
    private String balanceUpdateQueue;

    @Value("${rabbit.keys.balance-update}")
    private String balanceUpdateKey;


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurator) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurator.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setPrefetchCount(prefectCount);
        factory.setTaskExecutor(mqAskExecutor);
        return factory;
    }

    @Bean
    public TopicExchange balanceUpdateExchange() {
        return new TopicExchange(balanceUpdateExchange);
    }

    @Bean
    public Queue balanceUpdateQueue() {
        return QueueBuilder.durable(balanceUpdateQueue).build();
    }

    @Bean
    Binding balanceUpdateBinding(Queue balanceUpdateQueue, TopicExchange balanceUpdateExchange) {
        return BindingBuilder.bind(balanceUpdateQueue).to(balanceUpdateExchange).with(balanceUpdateKey);
    }
}
