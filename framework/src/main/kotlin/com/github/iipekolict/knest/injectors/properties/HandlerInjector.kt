package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Handler
import com.github.iipekolict.knest.injectors.PropertyInjector
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

class HandlerInjector : PropertyInjector<Handler, KFunction<*>?>() {

    override fun findAnnotation(): Handler? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): KFunction<*>? {
        return handler
    }
}