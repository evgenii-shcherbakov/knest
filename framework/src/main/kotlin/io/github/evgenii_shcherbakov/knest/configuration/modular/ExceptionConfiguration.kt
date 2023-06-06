package io.github.evgenii_shcherbakov.knest.configuration.modular

import io.github.evgenii_shcherbakov.knest.annotations.methods.DefaultExceptionHandler
import io.github.evgenii_shcherbakov.knest.annotations.methods.ExceptionHandler
import io.github.evgenii_shcherbakov.knest.configuration.ModularConfiguration
import io.github.evgenii_shcherbakov.knest.data.HandlerData
import io.github.evgenii_shcherbakov.knest.validators.ExceptionConfigurationValidator
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

object ExceptionConfiguration : ModularConfiguration<ExceptionConfiguration.Configuration>() {

    private var handlers: MutableSet<HandlerData> = mutableSetOf()

    class Configuration(val handlers: Set<HandlerData>) {

        val sortedHandlers: Set<HandlerData>
            get() {
                val defaultHandler = handlers.find { handler ->
                    handler.handler.annotations.any { it is DefaultExceptionHandler }
                }

                return if (defaultHandler != null) {
                    setOf(
                        *handlers.filter { handler ->
                            handler.handler.annotations.all {
                                it !is DefaultExceptionHandler
                            }
                        }.toTypedArray(),
                        defaultHandler
                    )
                } else {
                    handlers
                }
            }

        init {
            ExceptionConfigurationValidator.validate(this)
        }
    }

    override fun get(): Configuration {
        return Configuration(handlers)
    }

    fun setHandlers(vararg customHandlers: KFunction<*>) {
        handlers.addAll(
            customHandlers.mapNotNull { handler ->
                if (handlers.all { it.fullName != handler.name }) {
                    HandlerData(null, handler)
                } else {
                    null
                }
            }
        )
    }

    fun setContainers(vararg customContainers: Any) {
        handlers.addAll(
            customContainers
                .map { container ->
                    container::class.declaredMemberFunctions.mapNotNull { method ->
                        val isHandler: Boolean = method.annotations.any {
                            it is DefaultExceptionHandler || it is ExceptionHandler
                        }

                        val isExists: Boolean = handlers.any {
                            it.fullName == "${container::class.simpleName ?: ""}.${method.name}"
                        }

                        if (isHandler && !isExists) {
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