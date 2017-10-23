package com.cathcart93.sling.groovyworkflow.impl;

import com.cathcart93.sling.groovyworkflow.api.Memento;
import com.cathcart93.sling.groovyworkflow.api.Pipeline;
import com.cathcart93.sling.groovyworkflow.api.PipelineStepInfo;
import com.cathcart93.sling.groovyworkflow.api.Step;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus.FINISHED_SUCCESSFULLY;

public class PipelineImpl implements Pipeline {
    private List<PipelineStepImpl> steps;
    private volatile PipelineStatus status;

    public PipelineImpl(List<Step> steps) {
        this.steps = steps.stream().map(PipelineStepImpl::new).collect(Collectors.toList());
        this.status = PipelineStatus.NOT_STARTED;
    }

    public PipelineImpl(Step... steps) {
        this.steps = Arrays.stream(steps).map(PipelineStepImpl::new).collect(Collectors.toList());
        this.status = PipelineStatus.NOT_STARTED;
    }

    @Override
    public synchronized PipelineStatus getStatus() {
        return status;
    }

    @Override
    public void start() {
        if (status != PipelineStatus.NOT_STARTED) {
            throw new UnsupportedOperationException("Pipeline has already been started");
        }

        status = PipelineStatus.RUNNING;
        Object paramsForNextStep = null;
        for (PipelineStepImpl step : steps) {
            if (status != PipelineStatus.STOPPING) {
                step.start(paramsForNextStep);

                if (step.getStatus() == FINISHED_SUCCESSFULLY) {
                    paramsForNextStep = step.getResult();
                } else {
                    status = PipelineStatus.COMPLETED_WITH_ERROR;
                    break;
                }
            } else {
                status = PipelineStatus.STOPPED;
            }
        }

        if (status != PipelineStatus.COMPLETED_WITH_ERROR) {
            status = PipelineStatus.COMPLETED_SUCCESSFULLY;
        }
    }

    @Override
    public void stop() {
        if (status == PipelineStatus.COMPLETED_SUCCESSFULLY || status == PipelineStatus.COMPLETED_WITH_ERROR) {
            throw new UnsupportedOperationException("Pipeline has completed");
        } else if (status == PipelineStatus.STOPPING) {
            throw new UnsupportedOperationException("Pipeline is stopping");
        } else if (status == PipelineStatus.STOPPED) {
            throw new UnsupportedOperationException("Pipeline stopped");
        }

        status = PipelineStatus.STOPPING;
    }

    @Override
    public synchronized List<PipelineStepInfo> getStepsInfo() {
        return steps.stream().map(x -> (PipelineStepInfo) x).collect(Collectors.toList());
    }

    @Override
    public synchronized void inputParams(Object params) {

    }

    @Override
    public Memento save() {
        return null;
    }

    @Override
    public void restore(Memento snapshot) {

    }
}
