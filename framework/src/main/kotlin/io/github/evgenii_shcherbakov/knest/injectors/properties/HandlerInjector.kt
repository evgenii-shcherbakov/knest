package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Handler
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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