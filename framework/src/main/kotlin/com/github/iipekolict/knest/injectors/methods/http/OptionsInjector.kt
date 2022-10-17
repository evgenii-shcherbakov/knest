package com.github.iipekolict.knest.injectors.methods.http

import com.github.iipekolict.knest.annotations.methods.Options
import com.github.iipekolict.knest.data.EndpointData
import com.github.iipekolict.knest.injectors.methods.EndpointInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class OptionsInjector : EndpointInjector<Options>() {

    override fun findAnnotation(): Options? {
        return handler.findAnnotation()
    }

    override fun inject(): EndpointData {
        return EndpointData(
            paths = buildPaths(annotation.path, annotation.paths),
            method = HttpMethod.Options,
            handler = handler,
            swaggerCallback = swaggerCallback
        )
    }
}