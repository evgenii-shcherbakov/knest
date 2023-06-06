package io.github.evgenii_shcherbakov.knest.annotations.properties

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class AppConfig(val name: String = "", val isOptional: Boolean = false)