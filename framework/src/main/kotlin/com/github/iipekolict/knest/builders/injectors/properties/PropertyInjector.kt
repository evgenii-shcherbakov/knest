package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.builders.injectors.SuspendAnnotationInjector
import com.github.iipekolict.knest.exceptions.KNestException
import io.ktor.server.application.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.javaType

abstract class PropertyInjector<A : Annotation, P> : SuspendAnnotationInjector<A, P>() {

    protected lateinit var parameter: KParameter
    protected lateinit var call: ApplicationCall

    protected var exception: Exception? = null
    protected var handler: KFunction<*>? = null

    fun injectArgs(
        outParameter: KParameter,
        outCall: ApplicationCall,
        outException: Exception?,
        outHandler: KFunction<*>?
    ): PropertyInjector<A, P> {
        parameter = outParameter
        call = outCall
        exception = outException
        handler = outHandler

        return this
    }

    protected fun pipeParameter(value: Any?): Any? {
        try {
            if (value == null) return value

            return when (parameter.type.javaType) {
                String::class.java -> {
                    if (value is String) value else value.toString()
                }
                Int::class.java -> {
                    when (value) {
                        is String -> value.toString().toInt()
                        is Int -> value
                        is Float -> value.toInt()
                        is Double -> value.toInt()
                        else -> value.toString().toInt()
                    }
                }
                Float::class.java -> {
                    when (value) {
                        is String -> value.toFloat()
                        is Int -> value.toFloat()
                        is Float -> value
                        is Double -> value.toFloat()
                        else -> value.toString().toFloat()
                    }
                }
                Double::class.java -> {
                    when (value) {
                        is String -> value.toDouble()
                        is Int -> value.toDouble()
                        is Float -> value.toDouble()
                        is Double -> value
                        else -> value.toString().toFloat()
                    }
                }
                else -> value
            }
        } catch (e: Exception) {
            throw KNestException("Can't convert parameter to provided type")
        }
    }
}