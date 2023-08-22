package org.example.c8.basic.test;

/*
 * Copyright © 2021 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.DeployResourceCommandStep1;
import io.camunda.zeebe.client.api.response.ActivateJobsResponse;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.extension.testcontainer.ZeebeProcessTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@ZeebeProcessTest
/*
 * This annotation {@code import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;}
 * is recommended for Java 17+. It uses an embedded engine and is the fastest way
 * to run the tests.
 *
 * For Java 8+ use {@code import io.camunda.zeebe.process.test.extension.testcontainer.ZeebeProcessTest;)
 * It will start the embedded engine in a Docker container.
 *
 * Both implementations are interchangeable
 */
public class ApplicationTest {

    private final static String processResourceName = "process.bpmn";
    private final static String processKey = "process";
    private static final String setVarables1ServiceTask = "set-variables1";
    private static final String errorCode1 = "errorCode1";
    private static final String errorMessage1 = "Error Message 1";
    private static final String getVarables1ServiceTask = "get-variables1";

    // injected by ZeebeProcessTest annotation
    private ZeebeTestEngine engine;
    // injected by ZeebeProcessTest annotation
    private ZeebeClient client;

    @BeforeEach
    void deployProcesses() {
        // The embedded engine is completely reset before each test run.

        // Therefore, we need to deploy the process each time
        final DeploymentEvent deploymentEvent =
                deployResources(processResourceName);

        BpmnAssert.assertThat(deploymentEvent)
                .containsProcessesByResourceName(
                        processResourceName);
    }

    @Test
    void testErrorPath() throws InterruptedException, TimeoutException {
        // When

        //  -> create process instance
        final ProcessInstanceEvent processInstanceEvent =
                createInstance(processKey);

        BpmnAssert.assertThat(processInstanceEvent)
                .isStarted();

        //  -> complete the "set-variables1" service task with a bpmn error
        Map<String, Object> errorVariables = new HashMap<>();
        errorVariables.put("var1", "val1");
        errorVariables.put("var2", "val2");

        completeServiceTaskWithError(setVarables1ServiceTask, errorCode1, errorMessage1, errorVariables);

        //  -> complete the "get-variables1" service task
        completeServiceTask(getVarables1ServiceTask);

        // Then
        BpmnAssert.assertThat(processInstanceEvent)
                .isCompleted();
    }

    private DeploymentEvent deployResources(final String... resources) {
        final DeployResourceCommandStep1 commandStep1 = client.newDeployResourceCommand();

        DeployResourceCommandStep1.DeployResourceCommandStep2 commandStep2 = null;
        for (final String process : resources) {
            if (commandStep2 == null) {
                commandStep2 = commandStep1.addResourceFromClasspath(process);
            } else {
                commandStep2 = commandStep2.addResourceFromClasspath(process);
            }
        }

        return commandStep2.send().join();
    }

    /* These two methods deal with the asynchronous nature of the engine. It is recommended
     * to wait for an idle state before you assert on the state of the engine. Otherwise, you
     * may run into race conditions and flaky tests, depending on whether the engine
     * is still busy processing your last commands.
     *
     * Also note that many of the helper functions used in this test (e.g. {@code sendMessage(..)}
     * have a call to this method at the end. This is to ensure that each command sent to the engine
     * is fully processed before moving on. Without that you can run into issues, where e.g. you want
     * to complete a task, but the task has not been activated yet.
     *
     * Note that the duration is not like a {@code Thread.sleep()}. The tests will continue as soon as
     * an idle state is reached. Only if no idle state is reached during the {@code duration}
     * passed in as argument, then a timeout exception will be thrown.
     */
    private void waitForIdleState(final Duration duration)
            throws InterruptedException, TimeoutException {
        engine.waitForIdleState(duration);
    }

    private void waitForBusyState(final Duration duration)
            throws InterruptedException, TimeoutException {
        engine.waitForBusyState(duration);
    }

    private ProcessInstanceEvent createInstance(final String processKey)
            throws InterruptedException, TimeoutException {
        final ProcessInstanceEvent response = client.newCreateInstanceCommand()
                .bpmnProcessId(processKey)
                .latestVersion()
                .send()
                .join();
        waitForIdleState(Duration.ofSeconds(1));
        return response;
    }

    private void increaseTime(final Duration duration) throws InterruptedException, TimeoutException {
        // this method increases the time in a deterministic manner

        /* Process all existing commands to make sure that timer subscriptions related to the process
         * so far have been created
         */
        waitForIdleState(Duration.ofSeconds(1));

        /* Increase time in the engine. This will not take immediate effect, though. There is a
         * real-time delay of a couple of ms until the updated time is picked up by the scheduler
         */
        engine.increaseTime(duration);

        try {
            /* This code assumes that the increase of time will trigger timer events. Therefore, we wait
             * until the engine is busy. This means that it started triggering events.
             *
             * And after that, we wait for it to become idle again. That means it is waiting for new commands
             */
            waitForBusyState(Duration.ofSeconds(1));
            waitForIdleState(Duration.ofSeconds(1));
        } catch (final TimeoutException e) {
            // Do nothing. We've waited up to 1 second for processing to start, if it didn't start in this
            // time the engine probably has not got anything left to process.
        }
    }

    private void completeServiceTaskWithError(final String jobType, final String errorCode, final String errorMessage, Map<String, Object> errorVariables)
            throws InterruptedException, TimeoutException {
        completeServiceTasksWithError(jobType, errorCode, errorMessage, errorVariables, 1);
    }

    private void completeServiceTasksWithError(final String jobType, final String errorCode, final String errorMessage, Map<String, Object> errorVariables, final int count)
            throws InterruptedException, TimeoutException {

//        final var activateJobsResponse =
        final ActivateJobsResponse activateJobsResponse =
                client.newActivateJobsCommand().jobType(jobType).maxJobsToActivate(count).send().join();

        final int activatedJobCount = activateJobsResponse.getJobs().size();
        if (activatedJobCount < count) {
//            Assertions.fail(
//                    "Unable to activate %d jobs, because only %d were activated."
//                            .formatted(count, activatedJobCount));
            Assertions.fail("Unable to activate some or all jobs.");
        }

        for (int i = 0; i < count; i++) {
//            final var job = activateJobsResponse.getJobs().get(i);
            final ActivatedJob job = activateJobsResponse.getJobs().get(i);

//            client.newCompleteCommand(job.getKey()).send().join();
            client.newThrowErrorCommand(job.getKey())
                    .errorCode(errorCode)
                    .errorMessage(errorMessage)
                    .variables(errorVariables)
                    .send().join();
        }

        waitForIdleState(Duration.ofSeconds(1));
    }

    private void completeServiceTask(final String jobType)
            throws InterruptedException, TimeoutException {
        completeServiceTasks(jobType, 1);
    }

    private void completeServiceTasks(final String jobType, final int count)
            throws InterruptedException, TimeoutException {

//        final var activateJobsResponse =
        final ActivateJobsResponse activateJobsResponse =
                client.newActivateJobsCommand().jobType(jobType).maxJobsToActivate(count).send().join();

        final int activatedJobCount = activateJobsResponse.getJobs().size();
        if (activatedJobCount < count) {
//            Assertions.fail(
//                    "Unable to activate %d jobs, because only %d were activated."
//                            .formatted(count, activatedJobCount));
            Assertions.fail("Unable to activate some or all jobs.");
        }

        for (int i = 0; i < count; i++) {
//            final var job = activateJobsResponse.getJobs().get(i);
            final ActivatedJob job = activateJobsResponse.getJobs().get(i);

            logVariables(job);

            client.newCompleteCommand(job.getKey()).send().join();
        }

        waitForIdleState(Duration.ofSeconds(1));
    }

    private void logVariables(ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();

        if (variables.isEmpty()) {
            System.out.println("No variables");
        } else {
            // TODO: write something here to print variable names and values
            System.out.println("Some variables");

        }
    }
    private void completeUserTask(final String elementId)
            throws InterruptedException, TimeoutException {
        // user tasks can be controlled similarly to service tasks
        // all user tasks share a common job type
//        final var activateJobsResponse =
        final ActivateJobsResponse activateJobsResponse =
                client
                        .newActivateJobsCommand()
                        .jobType("io.camunda.zeebe:userTask")
                        .maxJobsToActivate(100)
                        .send()
                        .join();

        boolean userTaskWasCompleted = false;

        for (final ActivatedJob userTask : activateJobsResponse.getJobs()) {
            if (userTask.getElementId().equals(elementId)) {
                // complete the user task we care about
                client.newCompleteCommand(userTask).send().join();
                userTaskWasCompleted = true;
            } else {
                // fail all other user tasks that were activated
                // failing a task with a retry value >0 means the task can be reactivated in the future
                client.newFailCommand(userTask).retries(Math.max(userTask.getRetries(), 1)).send().join();
            }
        }

        waitForIdleState(Duration.ofSeconds(1));

        if (!userTaskWasCompleted) {
//            Assertions.fail("Tried to complete task `%s`, but it was not found".formatted(elementId));
            Assertions.fail("Tried to complete task, but it was not found");
        }
    }
}

