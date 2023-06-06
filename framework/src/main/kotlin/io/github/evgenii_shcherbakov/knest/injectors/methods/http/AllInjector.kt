package io.github.evgenii_shcherbakov.knest.injectors.methods.http

import io.github.evgenii_shcherbakov.knest.annotations.methods.All
import io.github.evgenii_shcherbakov.knest.data.EndpointData
import io.github.evgenii_shcherbakov.knest.injectors.methods.EndpointInjector
import kotlin.reflect.full.findAnnotation

class AllInjector : EndpointInjector<All>() {

    override fun findAnnotation(): All? {
        return handler.findAnnotation()
    }

    override fun inject(): EndpointData {
        return EndpointData(
            paths = buildPaths(annotation.path, annotation.paths),
            method = null,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}