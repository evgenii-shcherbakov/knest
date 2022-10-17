package com.github.iipekolict.knest.annotations.methods

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Authentication(val names: Array<String>)
