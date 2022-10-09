package com.github.iipekolict.knest.annotations.properties

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Header(val name: String = "")