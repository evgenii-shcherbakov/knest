package com.github.iipekolict.knest.injectors.methods.http

import com.github.iipekolict.knest.annotations.methods.Delete
import com.github.iipekolict.knest.data.EndpointData
import com.github.iipekolict.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class DeleteInjector : EndpointInjector<Delete>() {

    override fun findAnnotation(): Delete? {
        return handler.findAnnotation()
    }

    override fun inject(): EndpointData {
        return EndpointData(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Delete,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}