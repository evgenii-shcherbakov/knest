package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Res
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.server.response.*
import kotlin.reflect.full.findAnnotation

class ResInjector : PropertyInjector<Res, ApplicationResponse>() {

    override fun findAnnotation(): Res? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): ApplicationResponse {
        return call.response
    }
}