package com.example.knest

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.knest.controller.*
import com.example.knest.exceptions.ExceptionContainer
import com.example.knest.injectors.HttpVersionInjector
import com.example.knest.middlewares.MiddlewareContainer
import io.github.evgenii_shcherbakov.knest.KNest
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.response.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.setup() {
    val jwtSecret: String = environment.config.property("jwt.secret").getString()

    install(KNest) {
        framework {
            setControllers(
                MainController(),
                ExceptionController(),
                DecoratorController(),
                TypedController(),
                PipeController(),
                CustomDecoratorController(),
                MiddlewareController(),
                AuthController()
            )

            addPropertyInjectors(HttpVersionInjector::class)
        }

        exceptionHandling {
            setContainers(ExceptionContainer)
        }

        middleware {
            setContainers(MiddlewareContainer)
        }

        cors {
            anyHost()
        }

        contentNegotiation {
            gson {
                setPrettyPrinting()
            }
        }

        swagger {
            swagger {
                forwardRoot = true
            }

            info {
                title = "KNest preview API"
            }
        }

        authentication {
            basic("auth-basic") {
                validate { credentials ->
                    if (credentials.name == "test" && credentials.password == "123") {
                        UserIdPrincipal(credentials.name)
                    } else {
                        null
                    }
                }
            }

            jwt("auth-jwt") {
                verifier(JWT.require(Algorithm.HMAC256(jwtSecret)).build())

                validate { credentials ->
                    if (credentials.payload.getClaim("id").asInt() == 42) {
                        JWTPrincipal(credentials.payload)
                    } else {
                        null
                    }
                }

                challenge { defaultScheme, realm ->
                    call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
                }
            }
        }
    }
}

fun Application.launch() {
    setup()
}
