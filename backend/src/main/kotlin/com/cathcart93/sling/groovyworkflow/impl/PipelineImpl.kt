package com.cathcart93.sling.groovyworkflow.impl

import com.cathcart93.sling.groovyworkflow.api.Pipeline
import com.cathcart93.sling.groovyworkflow.api.PipelineStepResultStatus
import com.cathcart93.sling.groovyworkflow.api.StepResult
import rx.Observable
import java.lang.Exception
import java.util.concurrent.Callable

class PipelineImpl(private val steps: Collection<Callable<String>>) : Pipeline {

    override val output: Observable<StepResult>
        get() = Observable.create { subscriber ->
            for (step in steps) {
                if (subscriber.isUnsubscribed) {
                    break
                }
                try {
                    subscriber.onNext(StepResultImpl(StepOutputImpl(step.call()), PipelineStepResultStatus.FINISHED_SUCCESSFULLY))
                } catch (e: Exception) {
                    subscriber.onNext(StepResultImpl(StepOutputImpl(null), PipelineStepResultStatus.FINISHED_WITH_ERROR))
                    subscriber.onCompleted()
                    break
                }
            }
            if (!subscriber.isUnsubscribed) {
                subscriber.onCompleted()
            }
        }
}
