package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.exceptions.KNestException

@Controller("exception")
class ExceptionController {

    @Get("knest")
    suspend fun getKNestException(): String {
        throw KNestException("KNest exception")
    }

    @Get("default")
    suspend fun getDefaultError(): String {
        throw Exception("Default error")
    }
}