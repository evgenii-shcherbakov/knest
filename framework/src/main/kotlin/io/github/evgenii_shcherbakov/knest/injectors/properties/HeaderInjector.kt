package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Header
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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