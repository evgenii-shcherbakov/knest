package io.github.evgenii_shcherbakov.knest.injectors.methods

import io.github.evgenii_shcherbakov.knest.annotations.Swagger
import io.github.evgenii_shcherbakov.knest.injectors.NonSuspendAnnotationInjector
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.full.findAnnotation

class SwaggerInjector(
    private val target: KAnnotatedElement
) : NonSuspendAnnotationInjector<Swagger, OpenApiRoute.() -> Unit>() {

    override fun findAnnotation(): Swagger? {
        return target.findAnnotation()
    }

    override fun inject(): OpenApiRoute.() -> Unit {
        val swaggerAnnotation: Swagger? = target.findAnnotation()

        return if (swaggerAnnotation == null) {
            {}
        } else {
            {
                description = swaggerAnnotation.description
                summary = swaggerAnnotation.summary
                operationId = swaggerAnnotation.operationId
                securitySchemeName = swaggerAnnotation.securitySchemeName

                if (swaggerAnnotation.tags.isNotEmpty()) {
                    tags = swaggerAnnotation.tags.toList()
                }
            }
        }
    }
}