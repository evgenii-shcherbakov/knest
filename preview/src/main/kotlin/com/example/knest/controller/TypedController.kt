package com.example.knest.controller

import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.All
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get
import io.github.evgenii_shcherbakov.knest.annotations.methods.Post
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*

@Controller("typed")
class TypedController {

    @Get("call")
    suspend fun getCall(call: ApplicationCall): String {
        return call::class.simpleName ?: "No call"
    }

    @Get("app")
    suspend fun getApp(app: Application): String {
        return app::class.simpleName ?: "No app"
    }

    @Get("app-config")
    suspend fun getAppConfig(appConfig: ApplicationConfig): String {
        return appConfig::class.simpleName ?: "No app config"
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
        return req::class.simpleName ?: "No req"
    }

    @Get("res")
    suspend fun getRes(res: ApplicationResponse): String {
        return res::class.simpleName ?: "No res"
    }

    @Get("req-cookies")
    suspend fun getRequestCookies(cookies: RequestCookies): String {
        return cookies::class.simpleName ?: "No req cookies"
    }

    @Get("res-cookies")
    suspend fun getResponseCookies(cookies: ResponseCookies): String {
        cookies.append(Cookie("Test", "123"))
        return cookies["Test"]?.value ?: "No cookie"
    }

    @Get("res-headers")
    suspend fun getResHeaders(headers: ResponseHeaders): String {
        headers.append("Test", "123")
        return headers["Test"] ?: "No header"
    }
}