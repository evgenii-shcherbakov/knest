package com.github.iipekolict.preview

import com.github.iipekolict.knest.KNest
import com.github.iipekolict.preview.controller.*
import com.github.iipekolict.preview.exceptions.errorHandler
import io.ktor.serialization.gson.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.setup() {
    install(KNest) {
        setControllers(
            MainController(),
            ErrorController(),
            DecoratorController(),
            TypedController(),
            PipeController()
        )

        setErrorHandler(::errorHandler)

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
    }
}

fun Application.launch() {
    setup()
}
