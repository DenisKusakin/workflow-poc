package com.cathcart93.sling.groovyworkflow.impl.builders.xml.dto

import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
//@XmlAccessorType

data class StepDTO(@XmlAttribute val type: String = "", @XmlAttribute val script: String = "")