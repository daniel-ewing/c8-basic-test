package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.c8.basic.test.Application;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SetVariables2 {

    @JobWorker(type = "setVariables2")
    public Map<String, Object> handleSetVariables2(final ActivatedJob job) {
        String methodName = "handleSetVariables2";

        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
        if (Application.isLogJobEnabled) Application.logJob(methodName, job, null);

        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("aLong1", 21474836478l);
        variablesMap.put("aDouble1", 10293.84756d);

        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
        return variablesMap;
    }
}
