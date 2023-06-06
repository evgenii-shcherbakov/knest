package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.MiddlewareAnnotation
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import kotlin.reflect.full.findAnnotation

class MiddlewareAnnotationInjector : PropertyInjector<MiddlewareAnnotation, Annotation?>() {

    override fun findAnnotation(): MiddlewareAnnotation? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Annotation? {
        return middlewareAnnotation
    }
}