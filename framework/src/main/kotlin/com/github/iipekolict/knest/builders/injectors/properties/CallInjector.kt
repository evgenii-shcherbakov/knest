package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Call
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