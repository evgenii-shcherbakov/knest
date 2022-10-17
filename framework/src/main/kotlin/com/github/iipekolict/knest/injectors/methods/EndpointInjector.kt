package com.github.iipekolict.knest.injectors.methods

import com.github.iipekolict.knest.annotations.Swagger
import com.github.iipekolict.knest.data.EndpointData
import com.github.iipekolict.knest.injectors.NonSuspendAnnotationInjector
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

abstract class EndpointInjector<A : Annotation> : NonSuspendAnnotationInjector<A, EndpointData>() {

    protected lateinit var handler: KFunction<*>
    protected lateinit var controller: Any

    fun injectArgs(outHandler: KFunction<*>, outController: Any): EndpointInjector<A> {
        handler = outHandler
        controller = outController
        return this
    }

    protected fun buildPaths(path: String, paths: Array<String>): List<String> {
        return if (paths.isNotEmpty()) paths.toList() else listOf(path)
    }

    protected val swaggerCallback: OpenApiRoute.() -> Unit
        get() = SwaggerInjector(handler).inject()

    protected val swaggerAnnotation: Swagger?
        get() = handler.findAnnotation()
}