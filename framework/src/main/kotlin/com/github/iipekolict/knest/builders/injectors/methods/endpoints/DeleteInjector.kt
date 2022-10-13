package com.github.iipekolict.knest.builders.injectors.methods.endpoints

import com.github.iipekolict.knest.annotations.methods.Delete
import com.github.iipekolict.knest.data.Endpoint
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class DeleteInjector : EndpointInjector<Delete>() {

    override fun findAnnotation(): Delete? {
        return handler.findAnnotation()
    }

    override fun inject(): Endpoint {
        return Endpoint(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Delete,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}