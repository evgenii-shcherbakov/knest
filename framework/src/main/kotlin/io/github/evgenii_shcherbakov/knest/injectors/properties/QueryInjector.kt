package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Query
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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