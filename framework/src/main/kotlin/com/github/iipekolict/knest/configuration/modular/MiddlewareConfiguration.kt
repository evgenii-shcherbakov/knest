package com.github.iipekolict.knest.configuration.modular

import com.github.iipekolict.knest.annotations.methods.Middleware
import com.github.iipekolict.knest.configuration.ModularConfiguration
import com.github.iipekolict.knest.data.HandlerData
import com.github.iipekolict.knest.validators.MiddlewareConfigurationValidator
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

object MiddlewareConfiguration : ModularConfiguration<MiddlewareConfiguration.Configuration>() {

    private var middlewares: MutableSet<HandlerData> = mutableSetOf()

    class Configuration(val middlewares: Set<HandlerData>) {

        init {
            MiddlewareConfigurationValidator.validate(this)
        }
    }

    override val configuration: Configuration
        get() = Configuration(middlewares)

    fun setMiddlewares(vararg funcMiddlewares: KFunction<*>) {
        middlewares.addAll(
            funcMiddlewares.mapNotNull { middleware ->
                if (middlewares.all { it.fullName != middleware.name }) {
                    HandlerData(null, middleware)
                } else {
                    null
                }
            }
        )
    }

    fun setContainers(vararg middlewareContainers: Any) {
        middlewares.addAll(
            middlewareContainers
                .map { container ->
                    container::class.declaredMemberFunctions.mapNotNull { method ->
                        val isMiddleware: Boolean = method.annotations.any {
                            it is Middleware
                        }

                        val isExists: Boolean = middlewares.any {
                            it.fullName == "${container::class.simpleName ?: ""}.${method.name}"
                        }

                        if (isMiddleware && !isExists) {
                            HandlerData(container, method)
                        } else {
                            null
                        }
                    }
                }
                .flatten()
        )
    }
}