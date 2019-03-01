package com.lambdaschool.javacars;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class LogConsumer {

    @RabbitListener(queues = JavaCarsApplication.QUEUE_NAME)

    public void consumeMessage(final Message cm) {
        log.info("Received Message: {}", cm.toString());
    }
}
