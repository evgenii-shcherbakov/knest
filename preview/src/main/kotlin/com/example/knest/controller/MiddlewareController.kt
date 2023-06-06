package com.example.knest.controller

import com.example.knest.annotations.Check42
import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get
import io.github.evgenii_shcherbakov.knest.annotations.properties.ResHeaders
import io.ktor.server.response.*

@Controller("middleware")
class MiddlewareController {

    @Get("check42/{id}")
    @Check42("Middleware works!")
    suspend fun getCheck42Middleware(): String {
        return "Middleware not works!"
    }

    @Get("global")
    suspend fun getGlobalMiddleware(@ResHeaders headers: ResponseHeaders): String {
        return headers["Hello"] ?: "Middleware not works!"
    }
}