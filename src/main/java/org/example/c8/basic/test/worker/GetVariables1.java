package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetVariables1 {

    @ZeebeWorker(type = "get-variables1")
    public void handleGetVariables1(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables1: Enter");

//        Application.logJob("handleGetVariables1", job, null);

        Integer anInteger1 = (Integer)job.getVariablesAsMap().get("anInteger1");
        String aString1 = (String)job.getVariablesAsMap().get("aString1");
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables1: anInteger1 = {}, aString1 = {}", anInteger1, aString1);

        client.newCompleteCommand(job.getKey()).send().whenComplete((result, exception) -> {
                    if (exception == null) {
                        if (log.isDebugEnabled()) log.debug("Completed job successfully");
                    } else {
                        log.error("Failed to complete job", exception);
                    }
                });

        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables1: Exit");
    }
}
