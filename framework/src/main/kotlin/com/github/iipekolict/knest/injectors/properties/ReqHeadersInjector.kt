package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.ReqHeaders
import com.github.iipekolict.knest.injectors.PropertyInjector
import io.ktor.http.*
import kotlin.reflect.full.findAnnotation

class ReqHeadersInjector : PropertyInjector<ReqHeaders, Headers>() {

    override fun findAnnotation(): ReqHeaders? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Headers {
        return call.request.headers
    }
}