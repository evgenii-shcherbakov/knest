package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Body
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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