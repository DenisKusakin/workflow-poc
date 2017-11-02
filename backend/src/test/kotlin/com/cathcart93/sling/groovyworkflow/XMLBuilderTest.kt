package com.cathcart93.sling.groovyworkflow

import com.cathcart93.sling.groovyworkflow.impl.builders.XMLPipelineBuilder
import org.junit.jupiter.api.Test

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
class XMLBuilderTest {
    @Test
    internal fun test() {
        val builder = XMLPipelineBuilder("""
            <pipeline name="test name">
                <step type="groovy" script="script1"/>
                <step type="groovy" script="script2"/>
                <step type="groovy" script="script3"/>
            </pipeline>
        """.trimIndent())
        builder.getPipeline()
    }
}