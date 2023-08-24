package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class GetVariables {

    @ZeebeWorker(type = "getVariables")
    public void handleGetVariables(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables: Enter");

        Map<String, Object> currentVariables = job.getVariablesAsMap();
        currentVariables.forEach((k, v) -> {System.out.println("-----> %s - handleGetVariables: %s = %s".formatted(job.getBpmnProcessId(), k, v));});

        client.newCompleteCommand(job.getKey()).send().whenComplete((result, exception) -> {
                    if (exception == null) {
                        if (log.isDebugEnabled()) log.debug("Completed job successfully");
                    } else {
                        log.error("Failed to complete job", exception);
                    }
                });

        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables: Exit");
    }
}
