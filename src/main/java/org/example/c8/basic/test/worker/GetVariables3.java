package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeVariable;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.c8.basic.test.Application;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class GetVariables3 {

    @ZeebeWorker(type = "get-variables3", autoComplete = true)
    public void handleGetVariables3(final ActivatedJob job, @ZeebeVariable Boolean aBoolean1, @ZeebeVariable Long aDate1) {
        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables3: Enter");

        Application.logJob("handleGetVariables3", job, null);

        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables3: aBoolean1 = {}, aDate1 = {}", aBoolean1, new Date(aDate1));

        if (log.isDebugEnabled()) log.debug("-----> handleGetVariables3: Exit");
    }
}
