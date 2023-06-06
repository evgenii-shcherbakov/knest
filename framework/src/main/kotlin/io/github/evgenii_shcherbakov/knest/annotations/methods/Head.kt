package io.github.evgenii_shcherbakov.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Head(
    val path: String = "",
    val paths: Array<String> = []
)