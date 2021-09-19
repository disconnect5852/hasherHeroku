package com.txsanyix.asd;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitTemplates {
    public RabbitTemplates(RabbitTemplate inputTemplate, RabbitTemplate outputTemplate, Queue inputQueue, Queue outputQueue) {
        this.inputTemplate = inputTemplate;
        this.outputTemplate = outputTemplate;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    private RabbitTemplate inputTemplate;
    private RabbitTemplate outputTemplate;
    private Queue inputQueue;
    private Queue outputQueue;

    public RabbitTemplate getInputTemplate() {
        return inputTemplate;
    }

    public RabbitTemplate getOutputTemplate() {
        return outputTemplate;
    }

    public Queue getInputQueue() {
        return inputQueue;
    }

    public Queue getOutputQueue() {
        return outputQueue;
    }
}
