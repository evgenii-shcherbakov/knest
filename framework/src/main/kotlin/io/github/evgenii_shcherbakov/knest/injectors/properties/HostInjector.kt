package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Host
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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