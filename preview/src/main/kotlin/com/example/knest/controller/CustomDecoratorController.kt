package com.example.knest.controller

import com.example.knest.annotations.HttpVersion
import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get

@Controller("custom-decorator")
class CustomDecoratorController {

    @Get("http-version")
    suspend fun getHttpVersion(@HttpVersion httpVersion: String): String {
        return httpVersion
    }
}