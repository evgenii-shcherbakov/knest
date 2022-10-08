package com.github.iipekolict

import com.github.iipekolict.controller.MainController
import com.github.iipekolict.knest.KNest
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.launch() {
    install(KNest) {
        setControllers(MainController())

        cors {
            allowMethod(HttpMethod.Get)
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
