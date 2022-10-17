package com.github.iipekolict.knest.validators

import com.github.iipekolict.knest.annotations.methods.GlobalMiddleware
import com.github.iipekolict.knest.annotations.methods.Middleware
import com.github.iipekolict.knest.configuration.modular.MiddlewareConfiguration
import com.github.iipekolict.knest.data.HandlerData
import com.github.iipekolict.knest.exceptions.KNestException

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