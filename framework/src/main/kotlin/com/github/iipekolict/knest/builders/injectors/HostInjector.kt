package com.github.iipekolict.knest.builders.injectors

import com.github.iipekolict.knest.annotations.properties.Host
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class HostInjector : PropertyInjector<Host, String>() {

    override fun findAnnotation(): Host? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): String {
        return call.request.host()
    }
}