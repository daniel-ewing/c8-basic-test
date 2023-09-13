package org.example.c8.basic.test;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableZeebeClient
@Slf4j
@ZeebeDeployment(resources = "classpath*:/bpmn/**/*.bpmn")
public class Application {
    private final static String processKey = "process";

    @Autowired
    private ZeebeClient client;

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    private void ApplicationReadyEvent(ApplicationReadyEvent event) {
            if (log.isDebugEnabled()) log.debug("-----> ApplicationReadyEvent: Enter");
            for (int pi = 1; pi <= 1; pi++) {

                Map<String, Object> initVariables = new HashMap<>();
                initVariables.put("initVar1", "initVal");

                final ProcessInstanceResult processInstanceResult = client.newCreateInstanceCommand()
                        .bpmnProcessId(processKey)
                        .latestVersion()
                        .variables(initVariables)
                        .withResult()
                        .fetchVariables("errorVar", "localErrorVar")
                        .send()
                        .join(10, TimeUnit.SECONDS);

                processInstanceResult.getVariablesAsMap()
                        .forEach((k, v) -> System.out.println("-----> ApplicationReadyEvent: %s = %s".formatted(k, v)));

                if ((pi % 1000) == 0) {
                    if (log.isDebugEnabled()) log.debug("-----> ApplicationReadyEvent created: {} process instances", pi);
                }
            }

            if (log.isDebugEnabled()) log.debug("-----> ApplicationReadyEvent: Exit");
    }

    public static void logJob(final String caller, final ActivatedJob job, Object parameterValue) {
        log.info("-----> {}: logJob:\n" +
                "[type: {}, key: {}, element: {}, workflow instance: {}, deadline: {}]\n[headers: {}]\n[variables: {}]\n[parameter: {}]",
                caller,
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                job.getVariables(),
                parameterValue);
    }
}
