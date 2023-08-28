package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.c8.basic.test.Application;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SetVariables3 {

    @JobWorker(type = "setVariables3")
    public Map<String, Object> handleSetVariables3(final ActivatedJob job) {
        String methodName = "handleSetVariables3";

        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
        if (Application.isLogJobEnabled) Application.logJob(methodName, job, null);

        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("aBoolean1", true);
        variablesMap.put("aDate1", new Date());

        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
        return variablesMap;
    }
}
