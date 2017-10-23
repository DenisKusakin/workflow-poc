package com.cathcart93.sling.groovyworkflow;

import com.cathcart93.sling.groovyworkflow.api.Pipeline;
import com.cathcart93.sling.groovyworkflow.api.PipelineStep;
import com.cathcart93.sling.groovyworkflow.api.PipelineStepInfo;
import com.cathcart93.sling.groovyworkflow.api.Step;
import com.cathcart93.sling.groovyworkflow.impl.PipelineImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.stream.Collectors;

import static com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus.NOT_STARTED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PipelineStopTest {
    @Test
    void stopPipeline() throws Exception {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        Step step3 = mock(Step.class);
        Step step4 = mock(Step.class);

        when(step1.run(any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(5000);
                return null;
            }
        });

        Pipeline pipeline = new PipelineImpl(step1, step2, step3, step4);

        Thread task = new Thread(pipeline::start);
        task.start();
        Thread.sleep(500);
        pipeline.stop();
        Thread.sleep(6000);
        Assertions.assertEquals(Pipeline.PipelineStatus.STOPPED, pipeline.getStatus());
    }
}
