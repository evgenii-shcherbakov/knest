package io.github.evgenii_shcherbakov.knest.configuration.modular

import io.github.evgenii_shcherbakov.knest.annotations.methods.GlobalMiddleware
import io.github.evgenii_shcherbakov.knest.annotations.methods.Middleware
import io.github.evgenii_shcherbakov.knest.configuration.ModularConfiguration
import io.github.evgenii_shcherbakov.knest.data.HandlerData
import io.github.evgenii_shcherbakov.knest.validators.MiddlewareConfigurationValidator
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

object MiddlewareConfiguration : ModularConfiguration<MiddlewareConfiguration.Configuration>() {

    private var middlewares: MutableSet<HandlerData> = mutableSetOf()
    private var globalMiddlewares: MutableSet<HandlerData> = mutableSetOf()

    class Configuration(
        val middlewares: Set<HandlerData>,
        val globalMiddlewares: Set<HandlerData>
    ) {

        init {
            MiddlewareConfigurationValidator.validate(this)
        }
    }

    override fun get(): Configuration {
        return Configuration(middlewares, globalMiddlewares)
    }

    fun setMiddlewares(vararg funcMiddlewares: KFunction<*>) {
        funcMiddlewares.forEach { middleware ->
            if (middlewares.any { it.fullName == middleware.name }) return@forEach

            val handlerData = HandlerData(null, middleware)
            val isNotGlobal: Boolean = middleware.annotations.all { it !is GlobalMiddleware }
            val target: MutableSet<HandlerData> = if (isNotGlobal) middlewares else globalMiddlewares

            target.add(handlerData)
        }
    }

    fun setContainers(vararg middlewareContainers: Any) {
        middlewareContainers.forEach { container ->
            container::class.declaredMemberFunctions.forEach { method ->
                val isMiddleware: Boolean = method.annotations.any { it is Middleware }
                val isGlobalMiddleware: Boolean = method.annotations.any { it is GlobalMiddleware }

                val isExists: Boolean = middlewares.any {
                    it.fullName == "${container::class.simpleName ?: ""}.${method.name}"
                }

                if ((isMiddleware || isGlobalMiddleware) && !isExists) {
                    val handlerData = HandlerData(container, method)
                    val target: MutableSet<HandlerData> = if (isMiddleware) middlewares else globalMiddlewares

                    target.add(handlerData)
                }
            }
        }
    }
}