package com.github.iipekolict.knest.validators

import com.github.iipekolict.knest.annotations.methods.DefaultExceptionHandler
import com.github.iipekolict.knest.configuration.modular.ExceptionConfiguration
import com.github.iipekolict.knest.exceptions.KNestException

object ExceptionConfigurationValidator : ConfigurationValidator<ExceptionConfiguration.Configuration>() {

    private fun validateHandlers(handlers: Set<ExceptionConfiguration.Handler>) {
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