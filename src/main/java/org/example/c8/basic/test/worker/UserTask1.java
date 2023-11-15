package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.c8.basic.test.Application;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTask1 {
    @JobWorker(type = "io.camunda.zeebe:userTask", autoComplete = false, timeout = 120000L)
    public void handleUserTask1(final JobClient client, final ActivatedJob job) {
        String methodName = "handleUserTask1";

        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
        if (Application.isLogJobEnabled) Application.logJob(methodName, job, null);
        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
    }
}
