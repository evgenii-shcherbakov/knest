package com.github.iipekolict.knest.validators

import com.github.iipekolict.knest.annotations.methods.Middleware
import com.github.iipekolict.knest.configuration.modular.MiddlewareConfiguration
import com.github.iipekolict.knest.data.HandlerData
import com.github.iipekolict.knest.exceptions.KNestException

object MiddlewareConfigurationValidator : ConfigurationValidator<MiddlewareConfiguration.Configuration>() {

    private fun validateMiddlewares(middlewares: Set<HandlerData>) {
        val isAllAnnotated = middlewares.all { handlerData ->
            handlerData.handler.annotations.any { it is Middleware }
        }

        if (!isAllAnnotated) {
            throw KNestException("All middleware handlers should be annotated with 'Middleware' annotation")
        }
    }

    override fun validate(configuration: MiddlewareConfiguration.Configuration) {
        validateMiddlewares(configuration.middlewares)
    }
}