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
public class ThrowError2 {

    @ZeebeWorker(type = "throwError2")
    public void handleThrowError2(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleThrowError2: Enter");

        Map<String, Object> currentVariables = job.getVariablesAsMap();
        currentVariables.forEach((k, v) -> {System.out.println("-----> %s - handleThrowError2: %s = %s".formatted(job.getBpmnProcessId(), k, v));});

        Map<String, Object> errorVariables = new HashMap<>();
        errorVariables.put("errorVar2", "errorVal2");

        client.newThrowErrorCommand(job).errorCode("errorCode2")
                .errorMessage("Error Message 2").variables(errorVariables).send();

        if (log.isDebugEnabled()) log.debug("-----> handleThrowError2: Exit");
    }
}
