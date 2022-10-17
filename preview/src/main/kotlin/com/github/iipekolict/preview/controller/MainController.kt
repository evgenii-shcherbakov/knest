package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.Swagger
import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get

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