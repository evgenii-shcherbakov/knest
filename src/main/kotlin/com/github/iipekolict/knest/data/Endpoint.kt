package com.github.iipekolict.knest.data

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import kotlin.reflect.KFunction

class Endpoint(
    val paths: Array<String>,
    val method: HttpMethod?,
    val handler: KFunction<*>,
    val swaggerCallback: OpenApiRoute.() -> Unit
) {

    companion object {
        fun fromAnnotation(
            annotationMethod: HttpMethod?,
            annotationHandler: KFunction<*>,
            annotationPaths: Array<String>,
            annotationPath: String,
            annotationDescription: String,
            annotationSummary: String,
            annotationTags: Array<String>,
            annotationOperationId: String,
            annotationSecuritySchemeName: String,
        ): Endpoint {
            val endpointPaths: Array<String> = if (annotationPaths.isNotEmpty()) {
                annotationPaths
            } else {
                arrayOf(annotationPath)
            }

            return Endpoint(
                paths = endpointPaths,
                method = annotationMethod,
                handler = annotationHandler,
                swaggerCallback = {
                    description = annotationDescription.ifEmpty { null }
                    summary = annotationSummary.ifEmpty { null }
                    operationId = annotationOperationId.ifEmpty { null }
                    securitySchemeName = annotationSecuritySchemeName.ifEmpty { null }

                    if (annotationTags.isNotEmpty()) {
                        tags = annotationTags.toList()
                    }
                }
            )
        }
    }
}