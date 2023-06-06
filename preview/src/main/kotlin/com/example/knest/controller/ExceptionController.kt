package com.example.knest.controller

import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get
import io.github.evgenii_shcherbakov.knest.exceptions.KNestException

@Controller("exception")
class ExceptionController {

    @Get("knest")
    suspend fun getKNestException(): String {
        throw KNestException("KNest exception")
    }

    @Get("default")
    suspend fun getDefaultException(): String {
        throw Exception("Default exception")
    }
}