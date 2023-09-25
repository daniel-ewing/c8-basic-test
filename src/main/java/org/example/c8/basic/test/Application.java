package org.example.c8.basic.test;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.PublishMessageResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Application {


    public static void main(final String... args) {
        try (ZeebeClient client = ZeebeClient.newClientBuilder()
//                .caCertificatePath("/home/administrator/software/camunda/camunda-platform-8/certificates/dev.c8.separated.crt")
                .gatewayAddress("127.0.0.1:26500")
                .usePlaintext()
                .build()) {
            client.newTopologyRequest().send().join();


            final PublishMessageResponse publishMessageResponse = client.newPublishMessageCommand()
                    .messageName("start-process")
                    .correlationKey("")
                    .variables(Map.of("var1", "val1"))
                    .send()
                    .join();

//            CancelProcessInstanceResponse cancelProcessInstanceResponse = client.newCancelInstanceCommand(1L).send().join();

            client.newCancelInstanceCommand(1L)
                    .send()
                    .whenComplete((result, exception) -> {
                        if (exception == null) {
                            if (log.isDebugEnabled()) log.debug("Completed successfully");
                        } else {
                            log.error("Failed to complete", exception);
                        }
                    });


        }
    }
}
