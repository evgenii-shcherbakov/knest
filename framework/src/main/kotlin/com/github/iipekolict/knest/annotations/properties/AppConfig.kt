package com.github.iipekolict.knest.annotations.properties

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class AppConfig(val name: String = "", val isOptional: Boolean = false)