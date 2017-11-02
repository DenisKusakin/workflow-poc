package com.cathcart93.sling.groovyworkflow.impl.builders.xml

import com.cathcart93.sling.groovyworkflow.impl.builders.xml.dto.PipelineDTO
import com.cathcart93.sling.groovyworkflow.impl.builders.xml.dto.StepDTO
import org.apache.commons.io.IOUtils
import javax.xml.bind.JAXBContext
import javax.xml.transform.stream.StreamSource

fun parse(source: String): PipelineDTO? {
    return try {
        val context = JAXBContext.newInstance(PipelineDTO::class.java, StepDTO::class.java)
        val unmarshaller = context.createUnmarshaller()
        val sourceObj = StreamSource(IOUtils.toInputStream(source, "UTF-8"))
        val res = unmarshaller.unmarshal(sourceObj, PipelineDTO::class.java)
        res.value
    } catch (e: Exception) {
        null
    }
}