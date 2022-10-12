package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.builders.injectors.methods.endpoints.*
import com.github.iipekolict.knest.data.Endpoint
import com.github.iipekolict.knest.exceptions.KNestException
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.server.routing.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

class ControllerBuilder(private val routing: Routing, private val controller: Any) {

    private val controllerAnnotation: Controller
        get() = controller::class.findAnnotation()
            ?: throw KNestException("Controller should be annotated by Controller annotation")

    private val endpoints: List<Endpoint>
        get() {
            return controller::class.declaredMemberFunctions.map { method ->
                method.annotations.mapNotNull {
                    return@mapNotNull endpointInjectors.firstNotNullOfOrNull {
                        val instance = it.createInstance().injectArgs(method, controller)

                        return@firstNotNullOfOrNull if (instance.canActivate()) {
                            instance.inject()
                        } else {
                            null
                        }
                    }
                }
            }.flatten()
        }

    fun build() {
        routing.route(
            path = controllerAnnotation.path,
            builder = SwaggerInjector(controller::class).inject()
        ) {
            endpoints.map {
                EndpointBuilder(controller, this, it).build()
            }
        }
    }

    companion object {

        private val endpointInjectors = setOf(
            GetInjector::class,
            PostInjector::class,
            PatchInjector::class,
            PutInjector::class,
            DeleteInjector::class,
            HeadInjector::class,
            OptionsInjector::class,
            AllInjector::class
        )
    }
}