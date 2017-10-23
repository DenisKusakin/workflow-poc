package com.cathcart93.sling.groovyworkflow.api;

public interface Step {
    Object run(Object params);

    Memento save();

    void restore(Memento snapshot);
}
