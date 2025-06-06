package org.example.c8.basic.test;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@EnableScheduling
@Slf4j
public class PeriodicProcessStarter {

    private final ZeebeClient client;

    public PeriodicProcessStarter(ZeebeClient client) {
        this.client = client;
    }


    @Scheduled(fixedRate = 60000L)
    public void startProcessInstance(){
        String methodName = "startProcessInstance";

        if (log.isDebugEnabled()) log.debug("-----> {}: Enter", methodName);
        if (!Application.isPeriodicProcessStarterEnabled) return;

        Map<String, Object> variables = Application.generateVariables();

        // blocking / synchronous creation of a process instance => returns an instance
        ProcessInstanceEvent processInstanceEvent = client.newCreateInstanceCommand()
                .bpmnProcessId(Application.processKey)
                .latestVersion()
                .variables(
                        "{\"uuid\": \""
                                + UUID.randomUUID().toString()
                                + "\",\"startDateTime\": \""
                                + new Date()
                                + "\"}")
                .send()
                .join();

        if (log.isDebugEnabled()) logInstance(processInstanceEvent);

        if (log.isDebugEnabled()) log.debug("-----> {}: Exit", methodName);
    }

    private void logInstance(ProcessInstanceEvent event){
        if (log.isDebugEnabled()) log.debug(
                "started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
                event.getProcessDefinitionKey(),
                event.getBpmnProcessId(),
                event.getVersion(),
                event.getProcessInstanceKey());
    }
}
