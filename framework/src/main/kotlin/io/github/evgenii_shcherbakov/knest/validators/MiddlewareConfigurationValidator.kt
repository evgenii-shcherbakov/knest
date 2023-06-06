package io.github.evgenii_shcherbakov.knest.validators

import io.github.evgenii_shcherbakov.knest.annotations.methods.GlobalMiddleware
import io.github.evgenii_shcherbakov.knest.annotations.methods.Middleware
import io.github.evgenii_shcherbakov.knest.configuration.modular.MiddlewareConfiguration
import io.github.evgenii_shcherbakov.knest.data.HandlerData
import io.github.evgenii_shcherbakov.knest.exceptions.KNestException

object MiddlewareConfigurationValidator : ConfigurationValidator<MiddlewareConfiguration.Configuration>() {

    private fun validateMiddlewares(middlewares: Set<HandlerData>) {
        val isHasDoubleAnnotated = middlewares.any { handlerData ->
            handlerData.handler.annotations.any { it is GlobalMiddleware }
        }

        if (isHasDoubleAnnotated) {
            throw KNestException(
                "Middleware should have only one annotation ('Middleware'/'GlobalMiddleware')"
            )
        }
    }

    private fun validateGlobalMiddlewares(middlewares: Set<HandlerData>) {
        val isHasDoubleAnnotated = middlewares.any { handlerData ->
            handlerData.handler.annotations.any { it is Middleware }
        }

        if (isHasDoubleAnnotated) {
            throw KNestException(
                "Middleware should have only one annotation ('Middleware'/'GlobalMiddleware')"
            )
        }
    }

    override fun validate(configuration: MiddlewareConfiguration.Configuration) {
        validateMiddlewares(configuration.middlewares)
        validateGlobalMiddlewares(configuration.globalMiddlewares)
    }
}