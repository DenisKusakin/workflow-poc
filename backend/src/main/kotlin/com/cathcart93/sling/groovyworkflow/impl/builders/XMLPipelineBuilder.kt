package com.cathcart93.sling.groovyworkflow.impl.builders

import com.cathcart93.sling.groovyworkflow.api.Pipeline
import com.cathcart93.sling.groovyworkflow.api.PipelineBuilder
import com.cathcart93.sling.groovyworkflow.impl.builders.dto.PipelineDTO
import com.cathcart93.sling.groovyworkflow.impl.builders.dto.StepDTO
import org.apache.commons.io.IOUtils
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.transform.stream.StreamSource

/**
 * @author Denis_Kusakin. 11/1/2017.
 */
class XMLPipelineBuilder(private val source: String) : PipelineBuilder {
    override fun getPipeline(): Pipeline {
        try {
            val context = JAXBContext.newInstance(PipelineDTO::class.java, StepDTO::class.java)
            val unmarshaller = context.createUnmarshaller()
            val sourceObj = StreamSource(IOUtils.toInputStream(source, "UTF-8"))
            val res = unmarshaller.unmarshal(sourceObj, PipelineDTO::class.java)
            res.value
        }catch (e: Exception){
            System.out.print(e)
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}