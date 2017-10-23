package com.cathcart93.sling.groovyworkflow.impl;

import com.cathcart93.sling.groovyworkflow.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.cathcart93.sling.groovyworkflow.api.PipelineStep.PipelineStepStatus.FINISHED_SUCCESSFULLY;

public class PipelineQueueImpl implements Pipeline {
    //private List<PipelineStepImpl> steps;
    private volatile PipelineStatus status;
    private final ExecutorService pool;
    private volatile Queue<PipelineStepImpl> steps;

    public PipelineQueueImpl(Step... steps) {
        pool = Executors.newFixedThreadPool(1);
        //this.steps = Arrays.stream(steps).map(PipelineStepImpl::new).collect(Collectors.toList());
        for (Step step : steps) {
            this.steps.offer(new PipelineStepImpl(step));
        }
        this.status = PipelineStatus.NOT_STARTED;
    }

    @Override
    public synchronized PipelineStatus getStatus() {
        return status;
    }

    @Override
    public void start() {
        PipelineStepImpl step = this.steps.poll();

        CompletableFuture<PipelineStepImpl> res = CompletableFuture.supplyAsync(() -> {
            step.start(null);
            return step;
        }, pool);

        res.thenApply(o -> {
            if (o.getStatus() == PipelineStep.PipelineStepStatus.FINISHED_SUCCESSFULLY) {

            }
        }, pool);
    }

    @Override
    public void stop() {

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
