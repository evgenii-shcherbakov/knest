package io.github.evgenii_shcherbakov.knest.annotations.methods

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExceptionHandler(val type: KClass<out Exception>)