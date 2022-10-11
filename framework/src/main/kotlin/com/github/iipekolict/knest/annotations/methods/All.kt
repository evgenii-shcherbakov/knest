package com.github.iipekolict.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class All(
    val path: String = "",
    val paths: Array<String> = []
)