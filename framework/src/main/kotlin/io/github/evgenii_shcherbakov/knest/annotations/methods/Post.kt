package io.github.evgenii_shcherbakov.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Post(
    val path: String = "",
    val paths: Array<String> = []
)