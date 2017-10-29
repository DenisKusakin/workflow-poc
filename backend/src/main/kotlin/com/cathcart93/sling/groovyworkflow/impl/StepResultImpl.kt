package com.cathcart93.sling.groovyworkflow.impl

import com.cathcart93.sling.groovyworkflow.api.PipelineStepResultStatus
import com.cathcart93.sling.groovyworkflow.api.StepOutput
import com.cathcart93.sling.groovyworkflow.api.StepResult

data class StepResultImpl(override val result: StepOutput, override val status: PipelineStepResultStatus) : StepResult
