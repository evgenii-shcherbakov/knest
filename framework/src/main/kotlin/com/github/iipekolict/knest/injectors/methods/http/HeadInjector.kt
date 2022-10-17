package com.github.iipekolict.knest.injectors.methods.http

import com.github.iipekolict.knest.annotations.methods.Head
import com.github.iipekolict.knest.data.Endpoint
import com.github.iipekolict.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class HeadInjector : EndpointInjector<Head>() {

    override fun findAnnotation(): Head? {
        return handler.findAnnotation()
    }

    override fun inject(): Endpoint {
        return Endpoint(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Head,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}