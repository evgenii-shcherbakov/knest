package io.github.evgenii_shcherbakov.knest.builders

import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.data.EndpointData
import io.github.evgenii_shcherbakov.knest.exceptions.KNestException
import io.github.evgenii_shcherbakov.knest.injectors.methods.SwaggerInjector
import io.github.evgenii_shcherbakov.knest.injectors.methods.http.*
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.server.routing.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

class ControllerBuilder(private val routing: Routing, private val controller: Any) {

    private val controllerAnnotation: Controller
        get() = controller::class.findAnnotation()
            ?: throw KNestException("Controller should be annotated by Controller annotation")

    private val endpoints: List<EndpointData>
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