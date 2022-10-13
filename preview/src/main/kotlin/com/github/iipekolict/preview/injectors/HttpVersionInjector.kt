package com.github.iipekolict.preview.injectors

import com.github.iipekolict.knest.builders.injectors.properties.PropertyInjector
import com.github.iipekolict.preview.annotations.HttpVersion
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class HttpVersionInjector : PropertyInjector<HttpVersion, String>() {

    override fun findAnnotation(): HttpVersion? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): String {
        return call.request.httpVersion
    }
}