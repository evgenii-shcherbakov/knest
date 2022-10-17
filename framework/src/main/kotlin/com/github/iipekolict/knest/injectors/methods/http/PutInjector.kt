package com.github.iipekolict.knest.injectors.methods.http

import com.github.iipekolict.knest.annotations.methods.Put
import com.github.iipekolict.knest.data.EndpointData
import com.github.iipekolict.knest.injectors.methods.EndpointInjector
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