package com.github.iipekolict.preview.middlewares

import com.github.iipekolict.knest.annotations.methods.Middleware
import com.github.iipekolict.knest.annotations.properties.Param
import com.github.iipekolict.preview.annotations.Check42

object MiddlewareContainer {

    @Middleware(Check42::class)
    suspend fun check42Middleware(@Param("id") id: String): String? {
        return if (id == "42") "Middleware worked!" else null
    }
}