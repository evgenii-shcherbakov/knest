package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Body
import com.github.iipekolict.knest.injectors.PropertyInjector
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class BodyInjector : PropertyInjector<Body, Any?>() {

    override fun findAnnotation(): Body? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            pipeParameter(call.receive<Map<String, *>>()[annotation.name])
        } else {
            call.receive(annotation.type)
        }
    }
}