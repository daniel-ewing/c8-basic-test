package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetVariables1 {

    @ZeebeWorker(type = "set-variables1")
    public void handleSetVariables1(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables1: Enter");

//        Application.logJob("handleSetVariables1", job, null);

        String variables =
                "{" +
                "\"anInteger1\": 1," +
                "\"aString1\": \"string1\"" +
                "}";
        client.newCompleteCommand(job.getKey())
                .variables(variables).send().whenComplete((result, exception) -> {
                    if (exception == null) {
                        if (log.isDebugEnabled()) log.debug("Completed job successfully");
                    } else {
                        log.error("Failed to complete job", exception);
                    }
                });

        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables1: Exit");
    }
}
