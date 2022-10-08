package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get

@Controller(description = "Main controller")
class MainController {

    @Get(
        path = "test",
        description = "Health check"
    )
    suspend fun healthCheck(): Map<String, String> {
        return mapOf("status" to "ok")
    }

    @Get("array")
    suspend fun getArray(): Array<String> {
        return arrayOf("123")
    }
}