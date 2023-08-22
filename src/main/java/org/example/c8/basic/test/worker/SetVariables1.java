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
public class SetVariables1 {

    @ZeebeWorker(type = "set-variables1")
    public void handleSetVariables1(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables1: Enter");

//        Application.logJob("handleSetVariables1", job, null);
        Map<String, Object> errorVariables = new HashMap<>();
        errorVariables.put("var1", "val1");
        errorVariables.put("var2", "val2");
        client.newThrowErrorCommand(job).errorCode("errorCode1")
                .errorMessage("Error Message 1").variables(errorVariables).send();

//        String variables =
//                "{" +
//                "\"anInteger1\": 1," +
//                "\"aString1\": \"string1\"" +
//                "}";
//        client.newCompleteCommand(job.getKey())
//                .variables(variables).send().whenComplete((result, exception) -> {
//                    if (exception == null) {
//                        if (log.isDebugEnabled()) log.debug("Completed job successfully");
//                    } else {
//                        log.error("Failed to complete job", exception);
//                    }
//                });

        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables1: Exit");
    }
}
