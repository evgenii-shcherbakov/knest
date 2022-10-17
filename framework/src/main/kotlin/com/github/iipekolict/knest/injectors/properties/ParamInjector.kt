package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Param
import com.github.iipekolict.knest.injectors.PropertyInjector
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