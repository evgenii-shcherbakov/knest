package com.github.iipekolict.preview

import com.github.iipekolict.preview.controller.MainController
import com.github.iipekolict.knest.KNest
import com.github.iipekolict.preview.controller.DecoratorController
import com.github.iipekolict.preview.controller.PipeController
import com.github.iipekolict.preview.controller.TypedController
import io.ktor.serialization.gson.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.setup() {
    install(KNest) {
        setControllers(
            MainController(),
            DecoratorController(),
            TypedController(),
            PipeController()
        )

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
