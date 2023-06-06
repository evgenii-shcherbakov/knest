package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Method
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.http.*
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class MethodInjector : PropertyInjector<Method, HttpMethod>() {

    override fun findAnnotation(): Method? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): HttpMethod {
        return call.request.httpMethod
    }
}