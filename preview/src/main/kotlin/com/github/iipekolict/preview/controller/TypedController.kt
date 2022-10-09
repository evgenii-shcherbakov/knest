package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.All
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.annotations.methods.Post
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*

@Controller("typed")
class TypedController {

    @Get("call")
    suspend fun getCall(call: ApplicationCall): String {
        return call.javaClass.simpleName
    }

    @All("method")
    suspend fun getMethod(method: HttpMethod): String {
        return method.value
    }

    @Post("multipart-data")
    suspend fun getMultipartData(md: MultiPartData): List<String> {
        return md.readAllParts().mapNotNull { it.name }
    }

    @Get("req")
    suspend fun getReq(req: ApplicationRequest): String {
        return req.javaClass.simpleName
    }

    @Get("res")
    suspend fun getRes(res: ApplicationResponse): String {
        return res.javaClass.simpleName
    }

    @Get("req-cookies")
    suspend fun getRequestCookies(cookies: RequestCookies): Map<String, String> {
        return cookies.rawCookies
    }

    @Get("res-cookies")
    suspend fun getResponseCookies(cookies: ResponseCookies): String {
        cookies.append(Cookie("Test", "123"))
        return cookies["Test"]?.value ?: "No cookie"
    }

    @Get("res-headers")
    suspend fun getResHeaders(headers: ResponseHeaders): Map<String, List<String>> {
        headers.append("Test", "123")
        return headers.allValues().toMap()
    }
}