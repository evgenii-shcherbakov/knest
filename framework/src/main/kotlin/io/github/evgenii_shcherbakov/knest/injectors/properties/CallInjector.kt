package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Call
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.server.application.*
import kotlin.reflect.full.findAnnotation

class CallInjector : PropertyInjector<Call, ApplicationCall>() {

    override fun findAnnotation(): Call? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): ApplicationCall {
        return call
    }
}