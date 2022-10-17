package com.github.iipekolict.preview

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class TypedControllerTest {

    private fun testTypedString(path: String, expectedValue: String?, message: String) {
        return testApplication {
            client.get("/typed/$path").apply {
                assertEquals(expectedValue, bodyAsText(), message)
            }
        }
    }

    @Test
    fun testCall() = testTypedString(
        path = "call",
        expectedValue = RoutingApplicationCall::class.simpleName,
        message = "Call injecting broken"
    )

    @Test
    fun testApp() = testTypedString(
        path = "app",
        expectedValue = Application::class.simpleName,
        message = "App injecting broken"
    )

    @Test
    fun testAppConfig() = testTypedString(
        path = "app-config",
        expectedValue = HoconApplicationConfig::class.simpleName,
        message = "App config injecting broken"
    )

    @Test
    fun testMethod() = testTypedString(
        path = "method",
        expectedValue = "GET",
        message = "HttpMethod injecting broken"
    )

    @Test
    fun testRequest() = testTypedString(
        path = "req",
        expectedValue = RoutingApplicationRequest::class.simpleName,
        message = "Request injecting broken"
    )

    @Test
    fun testResponse() = testTypedString(
        path = "res",
        expectedValue = RoutingApplicationResponse::class.simpleName,
        message = "Response injecting broken"
    )

    @Test
    fun testRequestCookies() = testTypedString(
        path = "req-cookies",
        expectedValue = RequestCookies::class.simpleName,
        message = "Request cookies injecting broken"
    )

    @Test
    fun testResponseCookies() = testTypedString(
        path = "res-cookies",
        expectedValue = "123",
        message = "Response cookies injecting broken"
    )

    @Test
    fun testResponseHeaders() = testTypedString(
        path = "res-headers",
        expectedValue = "123",
        message = "Response headers injecting broken"
    )
}