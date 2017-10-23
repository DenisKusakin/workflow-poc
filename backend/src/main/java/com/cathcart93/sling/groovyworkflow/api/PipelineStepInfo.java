package com.cathcart93.sling.groovyworkflow.api;

public interface PipelineStepInfo {
    Object getResult();

    PipelineStep.PipelineStepStatus getStatus();
}
