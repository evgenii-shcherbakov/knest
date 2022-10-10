package com.github.iipekolict.knest.builders.injectors

import com.github.iipekolict.knest.annotations.properties.ReqPath
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class ReqPathInjector : PropertyInjector<ReqPath, String>() {

    override fun findAnnotation(): ReqPath? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): String {
        return call.request.path()
    }
}