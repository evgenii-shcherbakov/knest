package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Ip
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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