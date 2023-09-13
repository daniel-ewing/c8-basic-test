package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class GetVariables {

    @JobWorker(type = "getVariables", autoComplete = false)
    public void handleGetVariables(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables: Enter");

        Map<String, Object> currentVariables = job.getVariablesAsMap();
        currentVariables.forEach((k, v) -> System.out.println("-----> handleGetVariables: %s = %s".formatted(k, v)));

        client.newCompleteCommand(job.getKey()).send().whenComplete((result, exception) -> {
                    if (exception == null) {
                        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables: Completed job successfully");
                    } else {
                        log.error("-----> handleGetVariables: Failed to complete job", exception);
                    }
                });

        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables: Exit");
    }
}
