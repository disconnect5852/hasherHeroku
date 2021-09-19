package com.txsanyix.asd;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.txsanyix.asd.dto.JobElement;
import com.txsanyix.asd.utils.Utils;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.StandardCharsets;

public class Hasher {

    public static void main(String[] args) {
        final ApplicationContext rabbitConfig = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        final RabbitTemplates templates = rabbitConfig.getBean(RabbitTemplates.class);


        final SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(rabbitConfig.getBean(ConnectionFactory.class));
        listenerContainer.setQueues(templates.getInputQueue());



        listenerContainer.setMessageListener((MessageListener) message -> {
            final JobElement hashable = Utils.getMessage(message);
            if (hashable == null) return;
            System.out.println("Received todo job: " + hashable);

            byte[] hash = BCrypt.withDefaults().hash(15, hashable.getPassword().getBytes());
            hashable.setHash(new String(hash, StandardCharsets.UTF_8));

            System.out.println("Processed:" + hashable);
            templates.getOutputTemplate().convertAndSend(hashable);
        });


        listenerContainer.setErrorHandler(Throwable::printStackTrace);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Hasher");
            listenerContainer.shutdown();
        }));

        listenerContainer.start();
        System.out.println("Hasher started");
    }

}
