package com.example.knest.injectors

import com.example.knest.annotations.HttpVersion
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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