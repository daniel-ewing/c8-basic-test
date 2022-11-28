package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SetVariable1 {

    @JobWorker(type = "set-variable1")
    public void handleSetVariable1(final JobClient client, final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleSetVariable1: Enter");

        String variable1 = "1";

        client.newCompleteCommand(job.getKey())
                .variables(Map.of("variable1",variable1))
                .send()
                .exceptionally((throwable -> {
                    throw new RuntimeException("set-variable1 failed", throwable);
                }));

        if (log.isDebugEnabled()) log.debug("-----> handleSetVariable1: Exit");
    }
}
