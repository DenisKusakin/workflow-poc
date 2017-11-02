package com.cathcart93.sling.groovyworkflow.impl.builders.xml

import com.cathcart93.sling.groovyworkflow.api.Pipeline
import com.cathcart93.sling.groovyworkflow.api.PipelineBuilder
import com.cathcart93.sling.groovyworkflow.impl.builders.xml.dto.PipelineDTO
import com.cathcart93.sling.groovyworkflow.impl.builders.xml.dto.StepDTO
import org.apache.commons.io.IOUtils
import javax.xml.bind.JAXBContext
import javax.xml.transform.stream.StreamSource

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
class XMLPipelineBuilder(private val source: String) : PipelineBuilder {
    override fun getPipeline(): Pipeline {
        val dto = parse(source)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}