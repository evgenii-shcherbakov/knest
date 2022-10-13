package com.github.iipekolict.knest.builders.injectors.methods.endpoints

import com.github.iipekolict.knest.annotations.methods.Patch
import com.github.iipekolict.knest.data.Endpoint
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class PatchInjector : EndpointInjector<Patch>() {

    override fun findAnnotation(): Patch? {
        return handler.findAnnotation()
    }

    override fun inject(): Endpoint {
        return Endpoint(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Patch,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}