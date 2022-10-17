package com.github.iipekolict.knest.injectors.methods

import com.github.iipekolict.knest.annotations.Swagger
import com.github.iipekolict.knest.data.Endpoint
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

abstract class EndpointInjector<A : Annotation> : com.github.iipekolict.knest.injectors.NonSuspendAnnotationInjector<A, Endpoint>() {

    protected lateinit var handler: KFunction<*>
    protected lateinit var controller: Any

    fun injectArgs(outHandler: KFunction<*>, outController: Any): EndpointInjector<A> {
        handler = outHandler
        controller = outController
        return this
    }

    protected fun buildPaths(path: String, paths: Array<String>): Array<String> {
        return if (paths.isNotEmpty()) paths else arrayOf(path)
    }

    protected val swaggerCallback: OpenApiRoute.() -> Unit
        get() = com.github.iipekolict.knest.injectors.methods.SwaggerInjector(handler).inject()

    protected val swaggerAnnotation: Swagger?
        get() = handler.findAnnotation()
}