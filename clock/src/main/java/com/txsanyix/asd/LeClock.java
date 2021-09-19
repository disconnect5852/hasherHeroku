package com.txsanyix.asd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txsanyix.asd.dto.JobElement;
import com.txsanyix.asd.model.PutRoot;
import com.txsanyix.asd.model.Root;
import com.txsanyix.asd.utils.RestExceptionHandler;
import com.txsanyix.asd.utils.Utils;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeClock {

    private static final String REQUEST_URL = "https://realmonitor-senior-be-dev.appspot.com/api/job/v1/job:pull?applicant=SandorTarcsi";
    private static final String RESPONSE_URL = "https://realmonitor-senior-be-dev.appspot.com/api/job/v1/job/%d/result";
    private static final String MOCK_URL = "https://asdfgh.free.beeceptor.com/%d/result";

    private static final RestTemplate restTemplate = new RestTemplate();


    private static void generateJob(final long jobId, final List<String> passwords) {
        final ApplicationContext rabbitConfig = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        final RabbitTemplates templates = rabbitConfig.getBean(RabbitTemplates.class);
        passwords.forEach(p -> templates.getInputTemplate().convertAndSend(new JobElement(jobId, p)));
    }

    public static void main(String[] args) {
        System.out.println("Le clock starting");

        Root jobResponse = restTemplate.getForObject(REQUEST_URL, Root.class);
//        jobResponse.getJob().setPasswords(jobResponse.getJob().getPasswords().subList(0,10));

        System.out.println("Job size: " + jobResponse.getJob().getPasswords().size());
        generateJob(jobResponse.getJob().getId(), jobResponse.getJob().getPasswords());

        collectFinished(jobResponse.getJob().getId(), jobResponse.getJob().getPasswords().size());


    }

    private static void sendBatch(final Map<String, String> batch, long jobId) throws JsonProcessingException, HttpClientErrorException {
        restTemplate.setErrorHandler(new RestExceptionHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String valueAsString = new ObjectMapper().writerWithDefaultPrettyPrinter().forType(PutRoot.class).writeValueAsString(new PutRoot(batch));
        System.out.println("Response: " + valueAsString);
        HttpEntity<String> request = new HttpEntity<>(valueAsString, headers);
        System.out.println("Sending result:" + restTemplate.exchange(String.format(RESPONSE_URL, jobId), HttpMethod.PUT, request, String.class));
    }

    private static void collectFinished(long jobId, int jobSize) {
        final ApplicationContext rabbitConfig = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        final RabbitTemplates templates = rabbitConfig.getBean(RabbitTemplates.class);


        final SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(rabbitConfig.getBean(ConnectionFactory.class));
        listenerContainer.setQueues(templates.getOutputQueue());


        final Map<String, String> jobElementPackage = new HashMap<>();


        listenerContainer.setMessageListener((MessageListener) message -> {
            final JobElement element = Utils.getMessage(message);
            if (element == null) return;

            jobElementPackage.put(element.getPassword(), element.getHash());
            System.out.println("Received completed job: " + element + "  job result map size:" + jobElementPackage.size());
            if (jobElementPackage.size() >= jobSize) {
                System.out.println("batch done...");
                System.out.println(jobElementPackage);
                try {
                    sendBatch(jobElementPackage, jobId);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                listenerContainer.shutdown();
            }
        });

        listenerContainer.setErrorHandler(Throwable::printStackTrace);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown Le Clock");
            listenerContainer.shutdown();
        }));


        listenerContainer.start();
        System.out.println("Le Clock started");
    }
}
