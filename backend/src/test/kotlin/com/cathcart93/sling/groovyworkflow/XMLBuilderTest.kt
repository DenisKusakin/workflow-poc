package com.cathcart93.sling.groovyworkflow

import com.cathcart93.sling.groovyworkflow.impl.builders.xml.XMLPipelineBuilder
import com.cathcart93.sling.groovyworkflow.impl.builders.xml.parse
import org.junit.Assert
import org.junit.jupiter.api.Test

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
class XMLBuilderTest {
    @Test
    internal fun parserTest() {
        val testXML = """
            <pipeline name="test name">
                <step type="groovy" script="script1"/>
                <step type="groovy" script="script2"/>
                <step type="groovy" script="script3"/>
            </pipeline>
        """.trimIndent()
        val parsedDTO = parse(testXML)
        Assert.assertEquals("test name", parsedDTO?.name)
        val steps = parsedDTO?.steps
        val step1 = steps!![1]
        Assert.assertEquals("script2", step1.type)
    }
}