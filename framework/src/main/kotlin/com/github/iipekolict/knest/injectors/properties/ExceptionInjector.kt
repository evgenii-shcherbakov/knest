package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Exc
import com.github.iipekolict.knest.injectors.PropertyInjector
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