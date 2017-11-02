package com.cathcart93.sling.groovyworkflow.impl.builders.dto

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
@XmlAccessorType
class StepDTO{
    @XmlAttribute
    private lateinit var type: String
}