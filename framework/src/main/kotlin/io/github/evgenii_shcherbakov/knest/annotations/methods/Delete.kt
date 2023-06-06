package io.github.evgenii_shcherbakov.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Delete(
    val path: String = "",
    val paths: Array<String> = []
)