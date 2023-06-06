package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Param
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import kotlin.reflect.full.findAnnotation

class ParamInjector : PropertyInjector<Param, Any?>() {

    override fun findAnnotation(): Param? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            pipeParameter(call.parameters[annotation.name])
        } else {
            call.parameters
        }
    }
}