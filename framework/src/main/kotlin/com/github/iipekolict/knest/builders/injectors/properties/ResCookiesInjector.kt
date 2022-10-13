package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.ResCookies
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