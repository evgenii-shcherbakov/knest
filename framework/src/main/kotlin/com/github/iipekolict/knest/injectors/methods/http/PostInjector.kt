package com.github.iipekolict.knest.injectors.methods.http

import com.github.iipekolict.knest.annotations.methods.Post
import com.github.iipekolict.knest.data.Endpoint
import com.github.iipekolict.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class PostInjector : EndpointInjector<Post>() {

    override fun findAnnotation(): Post? {
        return handler.findAnnotation()
    }

    override fun inject(): Endpoint {
        return Endpoint(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Post,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}