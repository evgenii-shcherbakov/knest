package com.github.iipekolict.preview.middlewares

import com.github.iipekolict.knest.annotations.methods.GlobalMiddleware
import com.github.iipekolict.knest.annotations.methods.Middleware
import com.github.iipekolict.knest.annotations.properties.MiddlewareAnnotation
import com.github.iipekolict.knest.annotations.properties.Param
import com.github.iipekolict.knest.annotations.properties.ResHeaders
import com.github.iipekolict.preview.annotations.Check42
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