package com.github.iipekolict.knest.configuration

import com.github.iipekolict.knest.annotations.methods.DefaultExceptionHandler
import com.github.iipekolict.knest.annotations.methods.ExceptionHandler
import com.github.iipekolict.knest.validators.ExceptionConfigurationValidator
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

object ExceptionConfiguration : ModularConfiguration<ExceptionConfiguration.Configuration>() {

    private var handlers: MutableSet<Handler> = mutableSetOf()

    data class Handler(
        val container: Any?,
        val handler: KFunction<*>
    ) {

        val fullName: String
            get() {
                if (container == null || container::class.simpleName == null) {
                    return handler.name
                }

                return "${container::class.simpleName}.${handler.name}"
            }
    }

    class Configuration(val handlers: Set<Handler>) {

        val sortedHandlers: Set<Handler>
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

    override val configuration: Configuration
        get() = Configuration(handlers)

    fun setHandlers(vararg customHandlers: KFunction<*>) {
        handlers.addAll(
            customHandlers.mapNotNull { handler ->
                if (handlers.any { it.fullName == handler.name }) {
                    Handler(null, handler)
                } else {
                    null
                }
            }
        )
    }

    fun setContainers(vararg customContainers: Any) {
        handlers.addAll(
            customContainers.map { container ->
                container::class.declaredMemberFunctions.mapNotNull { method ->
                    val isHandler: Boolean = method.annotations.any {
                        it is DefaultExceptionHandler || it is ExceptionHandler
                    }

                    val isExists: Boolean = handlers.any {
                        it.fullName == "${container::class.simpleName ?: ""}.${method.name}"
                    }

                    if (isHandler && !isExists) {
                        Handler(container, method)
                    } else {
                        null
                    }
                }
            }.flatten()
        )
    }
}