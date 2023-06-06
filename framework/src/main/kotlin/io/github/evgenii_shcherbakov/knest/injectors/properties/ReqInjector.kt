package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Req
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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