package com.cathcart93.sling.groovyworkflow.impl;

import com.cathcart93.sling.groovyworkflow.api.Memento;
import com.cathcart93.sling.groovyworkflow.api.PipelineStep;
import com.cathcart93.sling.groovyworkflow.api.Step;

public class PipelineStepImpl implements PipelineStep {
    private Step step;
    private PipelineStepStatus status;
    private Object result;

    public PipelineStepImpl(Step step) {
        this.step = step;
        this.status = PipelineStepStatus.NOT_STARTED;
    }

    public synchronized void start(Object params) {
        if (status != PipelineStepStatus.NOT_STARTED) {
            throw new UnsupportedOperationException("Step has already been started");
        }
        status = PipelineStepStatus.RUNNING;
        try {
            result = step.run(params);
            status = PipelineStepStatus.FINISHED_SUCCESSFULLY;
        } catch (Exception e) {
            status = PipelineStepStatus.FINISHED_WITH_ERROR;
        }
    }

    public synchronized PipelineStepStatus getStatus() {
        return status;
    }

    @Override
    public Memento save() {
        return null;
    }

    @Override
    public void restore(Memento snapshot) {

    }

    public synchronized Object getResult() throws UnsupportedOperationException {
        if (status == PipelineStepStatus.FINISHED_SUCCESSFULLY) {
            return result;
        } else if (status == PipelineStepStatus.FINISHED_WITH_ERROR) {
            throw new UnsupportedOperationException("Step completed with exception");
        } else if (status == PipelineStepStatus.RUNNING) {
            throw new UnsupportedOperationException("Step is still running");
        }

        throw new UnsupportedOperationException("Step is not started");
    }
}
