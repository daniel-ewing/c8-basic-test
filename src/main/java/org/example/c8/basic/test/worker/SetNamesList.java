package org.example.c8.basic.test.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetNamesList {

    @ZeebeWorker(type = "setNamesList", autoComplete = true)
    public String handleSetNamesList(final ActivatedJob job) {
        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables3: Enter");

//        Application.logJob("handleSetVariables3", job, null);

        String names = "{\n" +
                "  \"names\": [\n" +
                "    {\n" +
                "      \"name\": \"george\",\n" +
                "      \"email\": \"george@beatles.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"john\",\n" +
                "      \"email\": \"john@beatles.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"paul\",\n" +
                "      \"email\": \"paul@beatles.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"ringo\",\n" +
                "      \"email\": \"ringo@beatles.com\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        if (log.isDebugEnabled()) log.debug("-----> handleSetVariables3: Exit");
        return names;
    }
}
