package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

@Controller("typed")
class TypedController {

    @Get("call")
    suspend fun getCall(call: ApplicationCall) {
        call.respondText("It's call!")
    }

    @Get("method")
    suspend fun getMethod(method: HttpMethod): HttpMethod {
        return method
    }
}