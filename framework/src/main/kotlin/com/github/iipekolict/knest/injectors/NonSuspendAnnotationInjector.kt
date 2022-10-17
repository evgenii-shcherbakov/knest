package com.github.iipekolict.knest.injectors

abstract class NonSuspendAnnotationInjector<A : Annotation, P> : AnnotationInjector<A>() {

    abstract fun inject(): P
}