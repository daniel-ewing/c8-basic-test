package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SetVariables2 {

    @ZeebeWorker(type = "set-variables2", autoComplete = true)
    public Map<String, Object> handleSetVariables2(final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables2: Enter");

//        Application.logJob("handleSetVariables2", job, null);

        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("aLong1", 21474836478l);
        variablesMap.put("aDouble1", 10293.84756d);

        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables2: Exit");
        return variablesMap;
    }
}
