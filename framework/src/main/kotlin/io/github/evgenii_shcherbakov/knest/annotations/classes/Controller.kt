package io.github.evgenii_shcherbakov.knest.annotations.classes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Controller(val path: String = "")