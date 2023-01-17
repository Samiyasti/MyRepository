package com.example.demo.rabbitamqp

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class RabbitAmqpConfig {

    @Bean
    fun queue(): Queue {
        return Queue("QueueA")
    }

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange("Direct Exchange")
    }

    @Bean
    fun binding(q: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(q).to(exchange).with("QueueA")
    }

    @Bean
    fun converter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }
    @Primary
    @Bean
    fun messageTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        var template: RabbitTemplate = RabbitTemplate(connectionFactory)
        template.messageConverter = converter()
        return template
    }
}