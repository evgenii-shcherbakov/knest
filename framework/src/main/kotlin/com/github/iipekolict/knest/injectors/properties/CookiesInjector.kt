package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Cookies
import com.github.iipekolict.knest.injectors.PropertyInjector
import kotlin.reflect.full.findAnnotation

class CookiesInjector : PropertyInjector<Cookies, Any?>() {

    override fun findAnnotation(): Cookies? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            pipeParameter(call.request.cookies[annotation.name])
        } else {
            call.request.cookies
        }
    }
}