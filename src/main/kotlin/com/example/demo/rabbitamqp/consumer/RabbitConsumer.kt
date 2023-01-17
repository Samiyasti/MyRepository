package com.example.demo.rabbitamqp.consumer

import com.example.demo.model.AppUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component


@Component
class RabbitConsumer {

    protected val logger: Log = LogFactory.getLog(javaClass)

    @RabbitListener(queues = ["QueueA"])
    fun consumeMessageFromQueue(user: AppUser) {
        logger.debug("message $user")
    }
}