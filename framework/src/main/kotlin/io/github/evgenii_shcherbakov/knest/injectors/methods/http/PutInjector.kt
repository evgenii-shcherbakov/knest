package io.github.evgenii_shcherbakov.knest.injectors.methods.http

import io.github.evgenii_shcherbakov.knest.annotations.methods.Put
import io.github.evgenii_shcherbakov.knest.data.EndpointData
import io.github.evgenii_shcherbakov.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class PutInjector : EndpointInjector<Put>() {

    override fun findAnnotation(): Put? {
        return handler.findAnnotation()
    }

    override fun inject(): EndpointData {
        return EndpointData(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Put,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}