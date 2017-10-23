package com.cathcart93.sling.groovyworkflow.api;

import java.util.List;

public interface Pipeline {
    PipelineStatus getStatus();

    void start();

    void stop();

    List<PipelineStepInfo> getStepsInfo();

    void inputParams(Object params);

    Memento save();

    void restore(Memento snapshot);

    enum PipelineStatus {
        RUNNING,
        COMPLETED_SUCCESSFULLY,
        COMPLETED_WITH_ERROR,
        STOPPED,
        STOPPING,
        WAITING_FOR_INPUT,
        NOT_STARTED
    }

}
