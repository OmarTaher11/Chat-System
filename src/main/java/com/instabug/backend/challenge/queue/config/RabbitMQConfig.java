package com.instabug.backend.challenge.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String CHAT_EXCHANGE = "chat-exchange";
    private static final String MESSAGE_EXCHANGE = "message-exchange";

    private static final String CHAT_QUEUE = "chat-queue";
    private static final String MESSAGE_MYSQL_QUEUE = "message-mysql-queue";
    private static final String MESSAGE_ELASTIC_QUEUE = "message-elastic-queue";
    private static final String MESSAGE_MYSQL_UPDATE_QUEUE = "message-mysql-update-queue";
    private static final String MESSAGE_ELASTIC_UPDATE_QUEUE = "message-elastic-update-queue";

    private static final String CHAT_ROUTING_KEY = "chat.routing.key";
    private static final String MESSAGE_MYSQL_ROUTING_KEY = "message.mysql.routing.key";
    private static final String MESSAGE_ELASTIC_ROUTING_KEY = "message.elastic.routing.key";
    private static final String MESSAGE_MYSQL_UPDATE_ROUTING_KEY = "message.mysql.update.routing.key";
    private static final String MESSAGE_ELASTIC_UPDATE_ROUTING_KEY = "message.elastic.update.routing.key";

    @Bean
    public DirectExchange chatExchange() {
        return new DirectExchange(CHAT_EXCHANGE);
    }

    @Bean
    public DirectExchange messageExchange() {
        return new DirectExchange(MESSAGE_EXCHANGE);
    }

    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE, true);
    }

    @Bean
    public Queue messageMySQLQueue() {
        return new Queue(MESSAGE_MYSQL_QUEUE, true);
    }

    @Bean
    public Queue messageElasticQueue() {
        return new Queue(MESSAGE_ELASTIC_QUEUE, true);
    }

    @Bean
    public Queue messageMySQLUpdateQueue() {
        return new Queue(MESSAGE_MYSQL_UPDATE_QUEUE, true);
    }

    @Bean
    public Queue messageElasticUpdateQueue() {
        return new Queue(MESSAGE_ELASTIC_UPDATE_QUEUE, true);
    }

    @Bean
    public Binding chatQueueBinding(Queue chatQueue, DirectExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with(CHAT_ROUTING_KEY);
    }

    @Bean
    public Binding messageMySQLQueueBinding(Queue messageMySQLQueue, DirectExchange messageExchange) {
        return BindingBuilder.bind(messageMySQLQueue).to(messageExchange).with(MESSAGE_MYSQL_ROUTING_KEY);
    }

    @Bean
    public Binding messageElasticQueueBinding(Queue messageElasticQueue, DirectExchange messageExchange) {
        return BindingBuilder.bind(messageElasticQueue).to(messageExchange).with(MESSAGE_ELASTIC_ROUTING_KEY);
    }

    @Bean
    public Binding messageMySQLUpdateQueueBinding(Queue messageMySQLQueue, DirectExchange messageExchange) {
        return BindingBuilder.bind(messageMySQLUpdateQueue()).to(messageExchange).with(MESSAGE_MYSQL_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Binding messageElasticUpdateQueueBinding(Queue messageElasticQueue, DirectExchange messageExchange) {
        return BindingBuilder.bind(messageElasticUpdateQueue()).to(messageExchange).with(MESSAGE_ELASTIC_UPDATE_ROUTING_KEY);
    }
}
