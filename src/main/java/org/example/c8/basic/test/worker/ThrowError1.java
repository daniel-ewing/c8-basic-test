package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ThrowError1 {

    @ZeebeWorker(type = "throwError1")
    public void handleThrowError1(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleThrowError1: Enter");

        Map<String, Object> currentVariables = job.getVariablesAsMap();
        currentVariables.forEach((k, v) -> {System.out.println("-----> %s - handleThrowError1: %s = %s".formatted(job.getBpmnProcessId(), k, v));});

        Map<String, Object> errorVariables = new HashMap<>();
        errorVariables.put("var1", "val1");

        client.newThrowErrorCommand(job).errorCode("errorCode1")
                .errorMessage("Error Message 1").variables(errorVariables).send();

        if (log.isDebugEnabled()) log.debug("-----> handleThrowError1: Exit");
    }
}
