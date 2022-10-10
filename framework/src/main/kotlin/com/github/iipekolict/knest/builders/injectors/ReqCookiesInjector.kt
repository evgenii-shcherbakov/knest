package com.github.iipekolict.knest.builders.injectors

import com.github.iipekolict.knest.annotations.properties.ReqCookies
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