package io.github.evgenii_shcherbakov.knest.builders

import io.github.evgenii_shcherbakov.knest.configuration.modular.FrameworkConfiguration
import io.github.evgenii_shcherbakov.knest.injectors.properties.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.createInstance
import kotlin.reflect.jvm.javaType

class ArgumentBuilder(
    private val parameter: KParameter,
    private val controller: Any,
    private val call: ApplicationCall,
    private val exception: Exception?,
    private val func: KFunction<*>?,
    private val middlewareAnnotation: Annotation?
) {

    private suspend fun convertParameterByType(): Any? {
        return when (parameter.type.javaType) {
            controller.javaClass -> controller
            ApplicationCall::class.java -> call
            HttpMethod::class.java -> call.request.local.method
            MultiPartData::class.java -> call.receiveMultipart()
            ApplicationRequest::class.java -> call.request
            ApplicationResponse::class.java -> call.response
            Headers::class.java -> call.request.headers
            ResponseHeaders::class.java -> call.response.headers
            RequestCookies::class.java -> call.request.cookies
            ResponseCookies::class.java -> call.response.cookies
            Exception::class.java -> exception?.cause
            KFunction::class.java -> func
            Application::class.java -> io.github.evgenii_shcherbakov.knest.GlobalStorage.getApp()
            ApplicationConfig::class.java -> io.github.evgenii_shcherbakov.knest.GlobalStorage.getAppConfig()
            else -> null
        }
    }

    private suspend fun convertParameterByAnnotation(): Any? {
        return propertyInjectors.firstNotNullOfOrNull {
            val instance = it
                .createInstance()
                .injectArgs(parameter, call, exception, func, middlewareAnnotation)

            if (instance.canActivate()) instance.inject() else null
        }
    }

    suspend fun build(): Any? {
        return convertParameterByType() ?: convertParameterByAnnotation()
    }

    companion object {

        private val propertyInjectors = setOf(
            ExceptionInjector::class,
            CallInjector::class,
            HostInjector::class,
            IpInjector::class,
            MethodInjector::class,
            ParamInjector::class,
            QueryInjector::class,
            HeaderInjector::class,
            BodyInjector::class,
            CookiesInjector::class,
            FileInjector::class,
            ReqInjector::class,
            ResInjector::class,
            ReqCookiesInjector::class,
            ResCookiesInjector::class,
            ReqHeadersInjector::class,
            ResHeadersInjector::class,
            ReqPathInjector::class,
            HandlerInjector::class,
            MiddlewareAnnotationInjector::class,
            AppInjector::class,
            AppConfigInjector::class,
            *FrameworkConfiguration.get().propertyInjectors.toTypedArray()
        )
    }
}