package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SetVariables3 {

    @ZeebeWorker(type = "set-variables3", autoComplete = true)
    public Map<String, Object> handleSetVariables3(final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables3: Enter");

//        Application.logJob("handleSetVariables3", job, null);

        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("aBoolean1", true);
        variablesMap.put("aDate1", new Date());

        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables3: Exit");
        return variablesMap;
    }
}
