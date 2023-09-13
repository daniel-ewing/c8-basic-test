package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DoSomething {

    @JobWorker(type = "doSomething")
    public void handleDoSomething(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleDoSomething: Enter");

        try {
            // Oh No, a RuntimeException!
            throw new RuntimeException("Error!");
        }
        catch (Exception e) {
            List<String> list = Collections.singletonList("errorVal");
            Map<String, Object> errorVariables = new HashMap<>();
            errorVariables.put("errorVar", list);

            client.newThrowErrorCommand(job).errorCode("errorCode")
                    .errorMessage("Error Message").variables(errorVariables).send();
        }

        if (log.isDebugEnabled()) log.debug("-----> handleDoSomething: Exit");
    }
}
