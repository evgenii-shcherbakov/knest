package io.github.evgenii_shcherbakov.knest.injectors.methods.http

import io.github.evgenii_shcherbakov.knest.annotations.methods.Options
import io.github.evgenii_shcherbakov.knest.data.EndpointData
import io.github.evgenii_shcherbakov.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class OptionsInjector : EndpointInjector<Options>() {

    override fun findAnnotation(): Options? {
        return handler.findAnnotation()
    }

    override fun inject(): EndpointData {
        return EndpointData(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Options,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}