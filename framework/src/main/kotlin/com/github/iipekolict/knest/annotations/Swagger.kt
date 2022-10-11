package com.github.iipekolict.knest.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Swagger(
    val description: String = "",
    val summary: String = "",
    val tags: Array<String> = [],
    val operationId: String = "",
    val securitySchemeName: String = ""
)