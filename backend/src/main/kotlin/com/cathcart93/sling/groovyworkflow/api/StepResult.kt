package com.cathcart93.sling.groovyworkflow.api

interface StepResult {
    val status: PipelineStepResultStatus

    val result: StepOutput

}
