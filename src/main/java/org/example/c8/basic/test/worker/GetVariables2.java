package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.c8.basic.test.Application;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetVariables2 {

    @JobWorker(type = "getVariables2")
    public void handleGetVariables2(final ActivatedJob job) {
        String methodName = "handleGetVariables2";

        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
        if (Application.isLogJobEnabled) Application.logJob(methodName, job, null);

        Long aLong1 = (Long)job.getVariablesAsMap().get("aLong1");
        Double aDouble1 = (Double)job.getVariablesAsMap().get("aDouble1");
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables2: aLong1 = {}, aDouble1 = {}", aLong1, aDouble1);

        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
    }
}
