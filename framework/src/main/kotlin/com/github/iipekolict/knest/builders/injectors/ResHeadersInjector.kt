package com.github.iipekolict.knest.builders.injectors

import com.github.iipekolict.knest.annotations.properties.ResHeaders
import io.ktor.server.response.*
import kotlin.reflect.full.findAnnotation

class ResHeadersInjector : PropertyInjector<ResHeaders, ResponseHeaders>() {

    override fun findAnnotation(): ResHeaders? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): ResponseHeaders {
        return call.response.headers
    }
}