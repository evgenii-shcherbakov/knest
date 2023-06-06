package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.ReqCookies
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class ReqCookiesInjector : PropertyInjector<ReqCookies, RequestCookies>() {

    override fun findAnnotation(): ReqCookies? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): RequestCookies {
        return call.request.cookies
    }
}