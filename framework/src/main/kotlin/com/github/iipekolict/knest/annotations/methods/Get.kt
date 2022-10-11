package com.github.iipekolict.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Get(
    val path: String = "",
    val paths: Array<String> = []
)