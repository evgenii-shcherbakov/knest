package com.example.knest.middlewares

import com.example.knest.annotations.Check42
import io.github.evgenii_shcherbakov.knest.annotations.methods.GlobalMiddleware
import io.github.evgenii_shcherbakov.knest.annotations.methods.Middleware
import io.github.evgenii_shcherbakov.knest.annotations.properties.MiddlewareAnnotation
import io.github.evgenii_shcherbakov.knest.annotations.properties.Param
import io.github.evgenii_shcherbakov.knest.annotations.properties.ResHeaders
import io.ktor.server.response.*

object MiddlewareContainer {

    @Middleware(Check42::class)
    suspend fun check42Middleware(
        @Param("id") id: String,
        @MiddlewareAnnotation annotation: Check42?
    ): String? {
        return if (id == "42") annotation?.message else null
    }

    @GlobalMiddleware
    suspend fun sendHeaderMiddleware(@ResHeaders headers: ResponseHeaders) {
        headers.append("Hello", "123")
    }
}