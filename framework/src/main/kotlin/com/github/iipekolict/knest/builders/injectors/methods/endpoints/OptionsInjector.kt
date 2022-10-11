package com.github.iipekolict.knest.builders.injectors.methods.endpoints

import com.github.iipekolict.knest.annotations.methods.Options
import com.github.iipekolict.knest.data.Endpoint
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class OptionsInjector : EndpointInjector<Options>() {

    override fun findAnnotation(): Options? {
        return handler.findAnnotation()
    }

    override fun inject(): Endpoint {
        return Endpoint(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Options,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}