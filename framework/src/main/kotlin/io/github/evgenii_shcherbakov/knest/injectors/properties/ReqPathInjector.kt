package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.ReqPath
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class ReqPathInjector : PropertyInjector<ReqPath, String>() {

    override fun findAnnotation(): ReqPath? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): String {
        return call.request.path()
    }
}