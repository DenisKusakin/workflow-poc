package com.cathcart93.sling.groovyworkflow.api;

public interface PipelineStep extends PipelineStepInfo{
    void start(Object params);

    Memento save();

    void restore(Memento snapshot);

    enum PipelineStepStatus {
        RUNNING,
        FINISHED_SUCCESSFULLY,
        FINISHED_WITH_ERROR,
        NOT_STARTED
    }
}
