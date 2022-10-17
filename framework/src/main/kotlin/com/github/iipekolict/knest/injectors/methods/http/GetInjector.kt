package com.github.iipekolict.knest.injectors.methods.http

import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.data.Endpoint
import com.github.iipekolict.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class GetInjector : EndpointInjector<Get>() {

    override fun findAnnotation(): Get? {
        return handler.findAnnotation()
    }

    override fun inject(): Endpoint {
        return Endpoint(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Get,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}