package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SetPeriodVariables {

    @ZeebeWorker(type = "setPeriodVariables", autoComplete = true)
    public Map<String, Object> handleSetPeriodVariables(final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> setPeriodVariables: Enter");

//        Application.logJob("setPeriodVariables", job, null);

        Map<String, Object> variablesMap = new HashMap<>();

        variablesMap.put("sop", "2022-04-01T07:00:00.000Z");
        variablesMap.put("eop", "2025-06-30T07:00:00.000Z");

        if (log.isDebugEnabled()) log.debug("-----> setPeriodVariables: Exit");
        return variablesMap;
    }
}
