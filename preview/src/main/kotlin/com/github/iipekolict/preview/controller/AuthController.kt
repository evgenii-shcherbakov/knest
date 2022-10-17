package com.github.iipekolict.preview.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Authentication
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.annotations.methods.Post
import com.github.iipekolict.knest.annotations.properties.App
import com.github.iipekolict.knest.annotations.properties.Body
import com.github.iipekolict.knest.annotations.properties.Call
import com.github.iipekolict.preview.dtos.BodyDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.time.Duration
import java.util.*

@Controller("auth")
class AuthController {

    @Post
    fun createToken(
        @App application: Application,
        @Body(type = BodyDto::class) dto: BodyDto
    ): String {
        return JWT.create()
            .withClaim("id", dto.id)
            .withClaim("name", dto.name)
            .withExpiresAt(Date(System.currentTimeMillis() + Duration.ofDays(1).toMillis()))
            .sign(Algorithm.HMAC256(application.environment.config.property("jwt.secret").getString()))
            ?: ""
    }

    @Get("basic")
    @Authentication(["auth-basic"])
    suspend fun getBasicAuth(@Call call: ApplicationCall): String {
        return call.principal<UserIdPrincipal>()?.name ?: "No user name"
    }

    @Get("jwt")
    @Authentication(["auth-jwt"])
    suspend fun getJwtAuth(@Call call: ApplicationCall): String {
        return call.principal<JWTPrincipal>()?.getClaim("id", String::class) ?: "No user id"
    }
}