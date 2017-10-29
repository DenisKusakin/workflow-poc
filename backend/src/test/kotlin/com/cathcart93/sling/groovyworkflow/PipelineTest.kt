package com.cathcart93.sling.groovyworkflow

import com.cathcart93.sling.groovyworkflow.api.PipelineStepResultStatus
import com.cathcart93.sling.groovyworkflow.api.StepResult
import com.cathcart93.sling.groovyworkflow.impl.PipelineImpl
import com.cathcart93.sling.groovyworkflow.impl.StepOutputImpl
import com.cathcart93.sling.groovyworkflow.impl.StepResultImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import rx.observers.TestSubscriber
import java.util.concurrent.Callable

class PipelineTest {
    @Test
    @DisplayName("Successful steps. Should return in correct order with correct output")
    internal fun successfulSteps() {
        val step1 = "First step"
        val step2 = "Second step"
        val step3 = "Third step"

        val subscriber = TestSubscriber<StepResult>()

        val pipeline = PipelineImpl(listOf(Callable { step1 }, Callable { step2 }, Callable { step3 }))

        pipeline.output.subscribe(subscriber)

        subscriber.assertCompleted()
        subscriber.assertNoErrors()
        val list = listOf<StepResult>(
                StepResultImpl(StepOutputImpl(step1), PipelineStepResultStatus.FINISHED_SUCCESSFULLY),
                StepResultImpl(StepOutputImpl(step2), PipelineStepResultStatus.FINISHED_SUCCESSFULLY),
                StepResultImpl(StepOutputImpl(step3), PipelineStepResultStatus.FINISHED_SUCCESSFULLY)
        )
        subscriber.assertReceivedOnNext(list)
    }

    @Test
    @DisplayName("Step with error. Skip steps after one completed with error")
    internal fun stepThrowsException() {
        val step1 = "First step"
        val step2 = "Second step"
        val step3 = "Third step"

        val stepThrowingException = Mockito.mock(Callable::class.java) as Callable<String>
        Mockito.`when`(stepThrowingException.call()).thenThrow(RuntimeException())

        val subscriber = TestSubscriber<StepResult>()

        val pipeline = PipelineImpl(listOf(Callable { step1 }, stepThrowingException, Callable { step2 }, Callable { step3 }))

        pipeline.output.subscribe(subscriber)

        subscriber.assertCompleted()
        subscriber.assertNoErrors()

        val list = listOf<StepResult>(
                StepResultImpl(StepOutputImpl(step1), PipelineStepResultStatus.FINISHED_SUCCESSFULLY),
                StepResultImpl(StepOutputImpl(null), PipelineStepResultStatus.FINISHED_WITH_ERROR)
        )
        subscriber.assertReceivedOnNext(list)
    }
}
