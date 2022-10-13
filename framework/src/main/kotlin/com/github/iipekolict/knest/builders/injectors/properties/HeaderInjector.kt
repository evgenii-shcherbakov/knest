package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Header
import kotlin.reflect.full.findAnnotation

class HeaderInjector : PropertyInjector<Header, Any?>() {

    override fun findAnnotation(): Header? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            pipeParameter(call.request.headers[annotation.name])
        } else {
            call.request.headers
        }
    }
}