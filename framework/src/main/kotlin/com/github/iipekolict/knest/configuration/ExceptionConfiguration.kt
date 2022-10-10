package com.github.iipekolict.knest.configuration

import com.github.iipekolict.knest.annotations.methods.DefaultExceptionHandler
import com.github.iipekolict.knest.annotations.methods.ExceptionHandler
import com.github.iipekolict.knest.validators.ExceptionConfigurationValidator
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

object ExceptionConfiguration {

    private var handlers: MutableSet<Handler> = mutableSetOf()

    class Handler(
        val container: Any?,
        val handler: KFunction<*>
    )

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

    val configuration: Configuration
        get() = Configuration(handlers)

    fun setHandlers(vararg customHandlers: KFunction<*>) {
        handlers.addAll(
            customHandlers.map {
                Handler(null, it)
            }
        )
    }

    fun setContainers(vararg customContainers: Any) {
        handlers.addAll(
            customContainers.map { controller ->
                controller::class.declaredMemberFunctions.mapNotNull { method ->
                    val isHandler: Boolean = method.annotations.any {
                        it is DefaultExceptionHandler || it is ExceptionHandler
                    }

                    if (isHandler) {
                        Handler(controller, method)
                    } else {
                        null
                    }
                }
            }.flatten()
        )
    }
}