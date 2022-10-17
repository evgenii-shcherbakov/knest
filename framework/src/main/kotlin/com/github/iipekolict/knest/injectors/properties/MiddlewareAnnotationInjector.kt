package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.MiddlewareAnnotation
import com.github.iipekolict.knest.injectors.PropertyInjector
import kotlin.reflect.full.findAnnotation

class MiddlewareAnnotationInjector : PropertyInjector<MiddlewareAnnotation, Annotation?>() {

    override fun findAnnotation(): MiddlewareAnnotation? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Annotation? {
        return middlewareAnnotation
    }
}