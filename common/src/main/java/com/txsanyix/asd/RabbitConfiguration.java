package com.txsanyix.asd;

import com.txsanyix.asd.utils.Utils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.System.getenv;

@Configuration
public class RabbitConfiguration {

    protected final String inputQueueName = "com.txsanyix.inputQueue";
    protected final String outputQueueName = "com.txsanyix.outputQueue";


    @Bean
    public ConnectionFactory connectionFactory() {
        final URI ampqUrl;
        try {
            ampqUrl = new URI(Utils.getEnvOrThrowOrNull("CLOUDAMQP_URL"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        final CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(ampqUrl.getUserInfo().split(":")[0]);
        factory.setPassword(ampqUrl.getUserInfo().split(":")[1]);
        factory.setHost(ampqUrl.getHost());
        factory.setPort(ampqUrl.getPort());
        factory.setVirtualHost(ampqUrl.getPath().substring(1));

        return factory;
    }

    @Bean
    public RabbitTemplates rabbitTemplates() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(this.inputQueueName);
        template.setQueue(this.inputQueueName);
        RabbitTemplate outTemplate = new RabbitTemplate(connectionFactory());
        outTemplate.setRoutingKey(this.outputQueueName);
        outTemplate.setQueue(this.outputQueueName);
        return new RabbitTemplates(template,outTemplate, new Queue(this.inputQueueName), new Queue(this.outputQueueName));
    }

}
