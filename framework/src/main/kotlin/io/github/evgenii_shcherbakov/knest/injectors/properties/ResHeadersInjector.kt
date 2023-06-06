package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.ResHeaders
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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