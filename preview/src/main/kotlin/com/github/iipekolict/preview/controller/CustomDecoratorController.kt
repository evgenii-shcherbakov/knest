package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.annotations.properties.*
import com.github.iipekolict.preview.annotations.HttpVersion
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*

@Controller("custom-decorator")
class CustomDecoratorController {

    @Get("http-version")
    suspend fun getHttpVersion(@HttpVersion httpVersion: String): String {
        return httpVersion
    }
}