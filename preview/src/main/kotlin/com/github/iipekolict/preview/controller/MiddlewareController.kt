package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.preview.annotations.Check42

@Controller("middleware")
class MiddlewareController {

    @Get("check42/{id}")
    @Check42("Middleware works!")
    suspend fun getCheck42Middleware(): String {
        return "Middleware not works!"
    }
}