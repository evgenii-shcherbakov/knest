package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.Res
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