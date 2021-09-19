package com.txsanyix.asd.utils;

import com.txsanyix.asd.dto.JobElement;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.util.Random;

import static java.lang.System.getenv;

public class Utils {

    private static final MessageConverter messageConverter = new SimpleMessageConverter();

    public static String generateRandomString(int length) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static JobElement getMessage(final Message message) {
        Object fromMessage = null;
        try {
            fromMessage = messageConverter.fromMessage(message);
        } catch (MessageConversionException e) {
            System.out.println("Wrong type: " + message);
            return null;
        }
        if (!(fromMessage instanceof JobElement)) {
            System.out.println("Wrong type: " + fromMessage);
            return null;
        }
        return (JobElement) fromMessage;
    }

    public static String getEnvOrThrowOrNull(String name) {
        final String env = getenv(name);
        if (env == null) {
            throw new IllegalStateException("Environment variable [" + name + "] is not set.");
        }
        return env;
    }
}
