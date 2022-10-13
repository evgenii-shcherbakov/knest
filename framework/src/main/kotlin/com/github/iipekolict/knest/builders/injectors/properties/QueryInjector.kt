package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Query
import kotlin.reflect.full.findAnnotation

class QueryInjector : PropertyInjector<Query, Any?>() {

    override fun findAnnotation(): Query? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            pipeParameter(call.request.queryParameters[annotation.name])
        } else {
            call.request.queryParameters
        }
    }
}