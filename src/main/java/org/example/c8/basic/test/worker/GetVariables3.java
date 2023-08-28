package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.slf4j.Slf4j;
import org.example.c8.basic.test.Application;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetVariables3 {

    // See JavaTimeConfigurator, this method signature is compatible with objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    @JobWorker(type = "getVariables3")
    public void handleGetVariables3(final ActivatedJob job, @Variable Boolean aBoolean1, @Variable String aDate1) {
        String methodName = "handleGetVariables3";

        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
        if (Application.isLogJobEnabled) Application.logJob(methodName, job, null);

        if (log.isDebugEnabled()) log.debug("-----> {}: aBoolean1 = {}, aDate1 = {}", methodName, aBoolean1, aDate1);

        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
    }

//    // See JavaTimeConfigurator, this method signature is compatible with objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
//    @JobWorker(type = "getVariables3")
//    public void handleGetVariables3(final ActivatedJob job, @Variable Boolean aBoolean1, @Variable Long aDate1) {
//        String methodName = "handleGetVariables3";
//
//        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
//        if (Application.isLogJobEnabled) Application.logJob(methodName, job, null);
//
//        if (log.isDebugEnabled()) log.debug("-----> {}: aBoolean1 = {}, aDate1 = {}", methodName, aBoolean1, new Date(aDate1));
//
//        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
//    }
}
