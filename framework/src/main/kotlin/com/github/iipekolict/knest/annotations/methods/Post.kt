package com.github.iipekolict.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Post(
    val path: String = "",
    val paths: Array<String> = []
)