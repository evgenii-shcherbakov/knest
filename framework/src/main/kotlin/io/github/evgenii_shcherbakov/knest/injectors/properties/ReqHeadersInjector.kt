package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.ReqHeaders
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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