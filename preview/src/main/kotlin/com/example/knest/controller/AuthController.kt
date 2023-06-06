package com.example.knest.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.knest.dtos.BodyDto
import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.Authentication
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get
import io.github.evgenii_shcherbakov.knest.annotations.methods.Post
import io.github.evgenii_shcherbakov.knest.annotations.properties.AppConfig
import io.github.evgenii_shcherbakov.knest.annotations.properties.Body
import io.github.evgenii_shcherbakov.knest.annotations.properties.Call
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import java.time.Duration
import java.util.*

@Controller("auth")
class AuthController {

    @Post
    fun createToken(
        @AppConfig("jwt.secret") jwtSecretProp: ApplicationConfigValue,
        @Body(type = BodyDto::class) dto: BodyDto
    ): String {
        return JWT.create()
            .withClaim("id", dto.id)
            .withClaim("name", dto.name)
            .withExpiresAt(Date(System.currentTimeMillis() + Duration.ofDays(1).toMillis()))
            .sign(Algorithm.HMAC256(jwtSecretProp.getString()))
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