package com.github.iipekolict.knest.builders.injectors.methods.endpoints

import com.github.iipekolict.knest.annotations.methods.Post
import com.github.iipekolict.knest.data.Endpoint
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