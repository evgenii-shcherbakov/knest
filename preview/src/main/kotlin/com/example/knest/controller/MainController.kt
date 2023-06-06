package com.example.knest.controller

import io.github.evgenii_shcherbakov.knest.annotations.Swagger
import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get

@Controller
@Swagger("Main controller")
class MainController {

    @Get("test")
    @Swagger(
        description = "Test",
        summary = "haha"
    )
    suspend fun healthCheck(): Map<String, String> {
        return mapOf("status" to "ok")
    }
}