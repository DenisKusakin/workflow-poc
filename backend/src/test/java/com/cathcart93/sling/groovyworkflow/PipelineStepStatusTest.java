package com.cathcart93.sling.groovyworkflow;

import com.cathcart93.sling.groovyworkflow.api.Pipeline;
import com.cathcart93.sling.groovyworkflow.api.PipelineStep;
import com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus;
import com.cathcart93.sling.groovyworkflow.api.PipelineStepInfo;
import com.cathcart93.sling.groovyworkflow.api.Step;
import com.cathcart93.sling.groovyworkflow.impl.PipelineImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus.FINISHED_SUCCESSFULLY;
import static com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus.FINISHED_WITH_ERROR;
import static com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus.NOT_STARTED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PipelineStepStatusTest {
    @Test
    @DisplayName("All steps should be in NOT_STARTED status before pipeline starting")
    void beforeStartAllStepsNotStarted() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        Step step3 = mock(Step.class);
        Step step4 = mock(Step.class);

        Pipeline pipeline = new PipelineImpl(step1, step2, step3, step4);

        List<PipelineStepInfo> infos = pipeline.getStepsInfo();
        List<PipelineStepStatus> statuses = infos.stream().map(PipelineStepInfo::getStatus).collect(Collectors.toList());
        PipelineStepStatus[] statusesArray = statuses.toArray(new PipelineStepStatus[infos.size()]);
        PipelineStepStatus[] expected = {NOT_STARTED, NOT_STARTED, NOT_STARTED, NOT_STARTED};
        Assertions.assertArrayEquals(expected, statusesArray);

        pipeline.start();
    }

    @Test
    @DisplayName("All steps should be in FINISHED_SUCCESSFULLY status after successful pipeline run")
    void startSuccessfulAllStepsCompletedSuccessfully() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        Step step3 = mock(Step.class);
        Step step4 = mock(Step.class);

        Pipeline pipeline = new PipelineImpl(step1, step2, step3, step4);

        pipeline.start();

        List<PipelineStepInfo> infos = pipeline.getStepsInfo();
        List<PipelineStepStatus> statuses = infos.stream().map(PipelineStepInfo::getStatus).collect(Collectors.toList());
        PipelineStepStatus[] statusesArray = statuses.toArray(new PipelineStepStatus[infos.size()]);
        PipelineStepStatus[] expected = {FINISHED_SUCCESSFULLY, FINISHED_SUCCESSFULLY, FINISHED_SUCCESSFULLY, FINISHED_SUCCESSFULLY};
        Assertions.assertArrayEquals(expected, statusesArray);
    }

    @Test
    @DisplayName("Step completed with error, its status should be COMPLETED_WITH_ERROR, steps before should be in COMPLETED_SUCCESSFULLY, steps after should be NOT_STARTED")
    void stepCompletedWithError() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        Step step3 = mock(Step.class);
        Step step4 = mock(Step.class);

        when(step2.run(any())).thenThrow(new RuntimeException());

        Pipeline pipeline = new PipelineImpl(step1, step2, step3, step4);

        pipeline.start();

        List<PipelineStepInfo> infos = pipeline.getStepsInfo();
        List<PipelineStepStatus> statuses = infos.stream().map(PipelineStepInfo::getStatus).collect(Collectors.toList());
        PipelineStepStatus[] statusesArray = statuses.toArray(new PipelineStepStatus[infos.size()]);
        PipelineStepStatus[] expected = {FINISHED_SUCCESSFULLY, FINISHED_WITH_ERROR, NOT_STARTED, NOT_STARTED};
        Assertions.assertArrayEquals(expected, statusesArray);
    }
}
