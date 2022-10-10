package com.github.iipekolict.knest.builders.injectors

import com.github.iipekolict.knest.annotations.properties.Req
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class ReqInjector : PropertyInjector<Req, ApplicationRequest>() {

    override fun findAnnotation(): Req? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): ApplicationRequest {
        return call.request
    }
}