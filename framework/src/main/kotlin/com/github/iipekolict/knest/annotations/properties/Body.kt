package com.github.iipekolict.knest.annotations.properties

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Body(val name: String = "", val type: KClass<*> = Any::class)