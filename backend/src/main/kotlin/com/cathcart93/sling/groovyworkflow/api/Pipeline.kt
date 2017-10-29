package com.cathcart93.sling.groovyworkflow.api

import rx.Observable

interface Pipeline {
    val output: Observable<StepResult>
}
