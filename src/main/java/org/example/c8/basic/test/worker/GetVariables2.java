package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetVariables2 {

    @ZeebeWorker(type = "get-variables2", autoComplete = true)
    public void handleGetVariables2(final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables2: Enter");

//        Application.logJob("handleGetVariables2", job, null);

        Long aLong1 = (Long)job.getVariablesAsMap().get("aLong1");
        Double aDouble1 = (Double)job.getVariablesAsMap().get("aDouble1");
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables2: aLong1 = {}, aDouble1 = {}", aLong1, aDouble1);

        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables2: Exit");
    }
}
