package com.cathcart93.sling.groovyworkflow.impl.builders.dto

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
@XmlRootElement(name="pipeline")
class PipelineDTO{
    @XmlElement(name="step")
    private lateinit var steps: List<StepDTO>

    @XmlAttribute
    private lateinit var name: String
}