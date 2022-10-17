package com.github.iipekolict.preview

import com.github.iipekolict.knest.KNest
import com.github.iipekolict.preview.controller.*
import com.github.iipekolict.preview.exceptions.ExceptionContainer
import com.github.iipekolict.preview.injectors.HttpVersionInjector
import com.github.iipekolict.preview.middlewares.MiddlewareContainer
import io.ktor.serialization.gson.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.setup() {
    install(KNest) {
        framework {
            setControllers(
                MainController(),
                ExceptionController(),
                DecoratorController(),
                TypedController(),
                PipeController(),
                CustomDecoratorController(),
                MiddlewareController()
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
    }
}

fun Application.launch() {
    setup()
}
