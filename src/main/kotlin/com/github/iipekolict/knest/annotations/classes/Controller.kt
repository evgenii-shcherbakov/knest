package com.github.iipekolict.knest.annotations.classes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Controller(
    val path: String = "",
    val description: String = "",
    val summary: String = "",
    val tags: Array<String> = [],
    val operationId: String = "",
    val securitySchemeName: String = ""
)