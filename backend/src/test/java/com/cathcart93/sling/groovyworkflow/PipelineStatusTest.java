package com.cathcart93.sling.groovyworkflow;

import com.cathcart93.sling.groovyworkflow.api.Memento;
import com.cathcart93.sling.groovyworkflow.api.Pipeline;
import com.cathcart93.sling.groovyworkflow.api.Step;
import com.cathcart93.sling.groovyworkflow.impl.PipelineImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PipelineStatusTest {
    @Test
    @DisplayName("Pipeline can be started 😱")
    void pipelineCanBeStarted() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        Step step3 = mock(Step.class);
        Step step4 = mock(Step.class);

        Pipeline pipeline = new PipelineImpl(step1, step2, step3, step4);

        pipeline.start();
    }

    @Test
    @DisplayName("Pipeline not started. Status should be NOT_STARTED")
    void pipelineStatusBeforeStart() {
        Step step1 = mock(Step.class);

        Pipeline pipeline = new PipelineImpl(step1);
        Assertions.assertSame(Pipeline.PipelineStatus.NOT_STARTED, pipeline.getStatus());
    }

    @Test
    @DisplayName("Status should be COMPLETED_SUCCESSFULLY in case of success")
    void pipelineStatusSuccessfullyFinished() {
        Step step1 = mock(Step.class);

        Pipeline pipeline = new PipelineImpl(step1);
        pipeline.start();
        Assertions.assertSame(Pipeline.PipelineStatus.COMPLETED_SUCCESSFULLY, pipeline.getStatus());
    }

    @Test
    @DisplayName("Status should be COMPLETED_WITH_ERROR in case of error")
    void pipelineStatusCompletedWithError() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        when(step2.run(any())).thenThrow(new RuntimeException());

        Pipeline pipeline = new PipelineImpl(step1, step2);
        pipeline.start();
        Assertions.assertSame(Pipeline.PipelineStatus.COMPLETED_WITH_ERROR, pipeline.getStatus());
    }
}
