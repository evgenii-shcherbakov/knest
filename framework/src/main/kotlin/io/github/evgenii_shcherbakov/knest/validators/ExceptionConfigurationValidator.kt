package io.github.evgenii_shcherbakov.knest.validators

import io.github.evgenii_shcherbakov.knest.annotations.methods.DefaultExceptionHandler
import io.github.evgenii_shcherbakov.knest.configuration.modular.ExceptionConfiguration
import io.github.evgenii_shcherbakov.knest.data.HandlerData
import io.github.evgenii_shcherbakov.knest.exceptions.KNestException

object ExceptionConfigurationValidator : ConfigurationValidator<ExceptionConfiguration.Configuration>() {

    private fun validateHandlers(handlers: Set<HandlerData>) {
        val defaultHandlersCount = handlers.count { handler ->
            handler.handler.annotations.any {
                it is DefaultExceptionHandler
            }
        }

        if (defaultHandlersCount > 1) {
            throw KNestException("Only one default exception handler allowed for App")
        }
    }

    override fun validate(configuration: ExceptionConfiguration.Configuration) {
        validateHandlers(configuration.handlers)
    }
}