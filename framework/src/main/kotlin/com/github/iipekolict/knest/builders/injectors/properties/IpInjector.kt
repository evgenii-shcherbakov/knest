package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Ip
import io.ktor.server.plugins.*
import kotlin.reflect.full.findAnnotation

class IpInjector : PropertyInjector<Ip, String>() {

    override fun findAnnotation(): Ip? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): String {
        return call.request.origin.remoteHost
    }
}