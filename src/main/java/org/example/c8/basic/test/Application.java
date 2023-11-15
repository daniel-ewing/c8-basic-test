package org.example.c8.basic.test;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
@EnableZeebeClient
@Slf4j
@Deployment(resources = "classpath*:/bpmn/**/*.bpmn")
public class Application {
    public final static boolean isLogJobEnabled = true;
    private final static String processKey = "process";

    private ZeebeClient client;

    public Application(ZeebeClient client) {
        this.client = client;
    }

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

//    @EventListener
//    private void ApplicationReadyEvent(ApplicationReadyEvent event) {
//            if (log.isDebugEnabled()) log.debug("-----> ApplicationReadyEvent: Enter");
//            for (int pi = 1; pi <= 1; pi++) {
//
//                // blocking / synchronous creation of a process instance => returns an instance
//                ProcessInstanceEvent processInstanceEvent = client.newCreateInstanceCommand()
//                        .bpmnProcessId(processKey)
//                        .latestVersion()
//                        .send()
//                        .join();
//
////                // non-blocking / asynchronous creation of a process instance  => returns a future
////                final ZeebeFuture<ProcessInstanceEvent> future = client.newCreateInstanceCommand()
////                        .bpmnProcessId(processKey)
////                        .latestVersion()
////                        .send();
//
//                if ((pi % 1000) == 0) {
//                    if (log.isDebugEnabled()) log.debug("-----> ApplicationReadyEvent created: {} process instances", pi);
//                }
//            }
//
//            if (log.isDebugEnabled()) log.debug("-----> ApplicationReadyEvent: Exit");
//    }

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
