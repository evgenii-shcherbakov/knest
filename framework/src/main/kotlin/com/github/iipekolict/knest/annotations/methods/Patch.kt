package com.github.iipekolict.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Patch(
    val path: String = "",
    val paths: Array<String> = []
)