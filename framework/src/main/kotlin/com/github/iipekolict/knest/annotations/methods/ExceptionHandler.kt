package com.github.iipekolict.knest.annotations.methods

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExceptionHandler(val type: KClass<out Exception>)