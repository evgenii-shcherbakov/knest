package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Cookies
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
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