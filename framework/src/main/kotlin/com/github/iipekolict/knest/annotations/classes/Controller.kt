package com.github.iipekolict.knest.annotations.classes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Controller(val path: String = "")