package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeVariable;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetResultVariables {

    @ZeebeWorker(type = "getResultVariables", autoComplete = true)
    public void handleGetResultVariables(final ActivatedJob job, @ZeebeVariable Integer getMonthsInPeriodResult, @ZeebeVariable String getDescriptionResult) {
        if (log.isDebugEnabled()) log.debug("-----> handleGetResultVariables: Enter");

//        Application.logJob("handleGetResultVariables", job, null);

        if (log.isDebugEnabled()) log.debug("-----> handleGetResultVariables: getMonthsInPeriodResult = {}", getMonthsInPeriodResult);
        if (log.isDebugEnabled()) log.debug("-----> handleGetResultVariables: getDescriptionResult = {}", getDescriptionResult);

        if (log.isDebugEnabled()) log.debug("-----> handleGetResultVariables: Exit");
    }
}
