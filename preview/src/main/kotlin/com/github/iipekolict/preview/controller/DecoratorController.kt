package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.annotations.methods.Post
import com.github.iipekolict.knest.annotations.properties.*
import com.github.iipekolict.preview.dtos.BodyDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.*

@Controller("decorator")
class DecoratorController {

    @Get("call")
    suspend fun getCall(@Call call: Any): String {
        return call.javaClass.name
    }

    @Get("param/{id}")
    suspend fun getParam(@Param("id") id: String): Map<String, Any> {
        return mapOf("id" to id)
    }

    @Get("param/{id}/all")
    suspend fun getAllParams(@Param params: Parameters): Map<String, List<String>> {
        return params.toMap()
    }

    @Get("query")
    suspend fun getQuery(@Query("id") id: String): Map<String, Any> {
        return mapOf("id" to id)
    }

    @Get("query/all")
    suspend fun getAllQuery(@Query query: Parameters): Map<String, List<String>> {
        return query.toMap()
    }

    @Get("header")
    suspend fun getHeader(@Header("Upgrade-Insecure-Requests") header: String?): String {
        return header ?: "No header"
    }

    @Get("header/all")
    suspend fun getAllHeaders(@Header headers: Headers): Map<String, List<String>> {
        return headers.toMap()
    }

    @Post("body")
    suspend fun getBodyField(@Body("id") id: Int): Int {
        return id
    }

    @Post("body/all")
    suspend fun getBody(@Body(type = BodyDto::class) dto: BodyDto): BodyDto {
        return dto
    }
}