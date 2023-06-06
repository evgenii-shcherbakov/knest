package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.ResCookies
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.server.response.*
import kotlin.reflect.full.findAnnotation

class ResCookiesInjector : PropertyInjector<ResCookies, ResponseCookies>() {

    override fun findAnnotation(): ResCookies? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): ResponseCookies {
        return call.response.cookies
    }
}