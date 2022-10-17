package com.github.iipekolict.preview.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Check42(val message: String = "")