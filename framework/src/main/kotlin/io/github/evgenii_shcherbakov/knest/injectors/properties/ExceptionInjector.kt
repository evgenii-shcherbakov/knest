package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.annotations.properties.Exc
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import kotlin.Exception
import kotlin.reflect.full.findAnnotation

class ExceptionInjector : PropertyInjector<Exc, Exception?>() {

    override fun findAnnotation(): Exc? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Exception? {
        return exception?.cause as? Exception ?: exception
    }
}