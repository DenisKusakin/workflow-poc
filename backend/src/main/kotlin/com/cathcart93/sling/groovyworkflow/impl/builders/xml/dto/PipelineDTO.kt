package com.cathcart93.sling.groovyworkflow.impl.builders.xml.dto

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
@XmlRootElement(name = "pipeline")
data class PipelineDTO(@XmlElement(name = "step") val steps: List<StepDTO> = emptyList(), @XmlAttribute val name: String = "")