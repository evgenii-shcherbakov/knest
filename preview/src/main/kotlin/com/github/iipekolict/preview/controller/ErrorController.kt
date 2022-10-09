package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get

@Controller("error")
class ErrorController {

    @Get
    suspend fun getError(): String {
        throw RuntimeException("Some error")
    }
}