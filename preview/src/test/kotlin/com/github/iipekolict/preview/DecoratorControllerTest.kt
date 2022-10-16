package com.github.iipekolict.preview

import com.github.iipekolict.preview.dtos.ParameterDto
import com.github.iipekolict.preview.dtos.ParameterMapDto
import com.github.iipekolict.preview.dtos.TestBodyDto
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DecoratorControllerTest {

    private val number = 42
    private val testName = "test"
    private val testValue = "123"
    private val testBody = TestBodyDto(number, "John")

    private fun testStringResponse(path: String, name: String, block: (response: String) -> Boolean) {
        return testApplication {
            client.get("/decorator/$path").apply {
                val response: String = bodyAsText()

                assertNotNull(response, "Response shouldn't be empty")

                assertTrue("$name decorator was broken") {
                    block(response)
                }
            }
        }
    }

    @Test
    fun testCallDecorator() = testStringResponse("call", "Call") {
        it == RoutingApplicationCall::class.simpleName
    }

    @Test
    fun testIpDecorator() = testStringResponse("ip", "Ip") {
        it == "localhost"
    }

    @Test
    fun testHostDecorator() = testStringResponse("host", "Host") {
        it == "localhost"
    }

    @Test
    fun testMethodDecorator() = testStringResponse("method", "Method") {
        it == "GET"
    }

    @Test
    fun testRequestDecorator() = testStringResponse("req", "Request") {
        it == RoutingApplicationRequest::class.simpleName
    }

    @Test
    fun testResponseDecorator() = testStringResponse("res", "Response") {
        it == RoutingApplicationResponse::class.simpleName
    }

    @Test
    fun testRequestCookiesDecorator() = testStringResponse("req-cookies", "ReqCookies") {
        it == RequestCookies::class.simpleName
    }

    @Test
    fun testResponseCookiesDecorator() = testStringResponse("res-cookies", "ResCookies") {
        it == "123"
    }

    @Test
    fun testRequestHeadersDecorator() = testStringResponse("req-headers", "ReqHeaders") {
        it == HeadersImpl::class.simpleName
    }

    @Test
    fun testResponseHeadersDecorator() = testStringResponse("res-headers", "ResHeaders") {
        it == "123"
    }

    @Test
    fun testRequestPathDecorator() = testStringResponse("req-path", "ReqPath") {
        it == "/decorator/req-path"
    }

    private fun testParameter(singlePath: String, allPath: String, name: String) {
        return testApplication {
            val httpClient = createClient {
                install(ContentNegotiation) {
                    gson()
                }
            }

            val failMessage = "$name decorator broken"

            httpClient.get("/decorator/$singlePath").apply {
                val response: ParameterDto = body()

                assertNotNull(response, "Response shouldn't be empty")
                assertEquals(number.toString(), response.id, failMessage)
            }

            httpClient.get("/decorator/$allPath").apply {
                val response: ParameterMapDto = body()

                assertNotNull(response, "Response shouldn't be empty")
                assertEquals(number.toString(), response.id[0], failMessage)
            }
        }
    }

    @Test
    fun testParamDecorator() = testParameter(
        singlePath = "/param/$number",
        allPath = "/param/$number/all",
        name = "Param"
    )

    @Test
    fun testQueryDecorator() = testParameter(
        singlePath = "/query?id=$number",
        allPath = "/query/all?id=$number",
        name = "Query"
    )

    @Test
    fun testHeaderDecorator() = testApplication {
        val httpClient = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val singleResponse: String = httpClient
            .get("/decorator/header") { header(testName, testValue) }
            .bodyAsText()

        val allResponse: Map<String, List<String>>? = httpClient
            .get("/decorator/header/all") { header(testName, testValue) }
            .body()

        assertTrue("Response shouldn't be empty") {
            singleResponse.isNotEmpty() && allResponse != null
        }

        assertTrue("Header decorator broken") {
            (singleResponse == testValue) || (allResponse?.get(testName)?.get(0) == testValue)
        }
    }

    @Test
    fun testBodyDecorator() = testApplication {
        val httpClient = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val id: String = httpClient
            .post("/decorator/body") {
                setBody(testBody)
                header("content-type", "application/json")
            }
            .bodyAsText()

        val body: TestBodyDto? = httpClient
            .post("/decorator/body/all") {
                setBody(testBody)
                header("content-type", "application/json")
            }
            .body()

        assertTrue("Response shouldn't be empty") {
            id.isNotEmpty() && body != null
        }

        assertTrue("Body decorator broken") {
            id == testBody.id.toString() || body == testBody
        }
    }

    @Test
    fun testCookiesDecorator() = testApplication {
        val httpClient = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val singleCookie: String = httpClient
            .get("/decorator/cookies") { cookie(testName, testValue) }
            .bodyAsText()

        val allCookies: Map<String, String>? = httpClient
            .get("/decorator/cookies/all") { cookie(testName, testValue) }
            .body()

        assertTrue("Response shouldn't be empty") {
            singleCookie.isNotEmpty() && allCookies != null
        }

        assertTrue("Cookies decorator broken") {
            singleCookie == testValue || allCookies?.get(testName) == testValue
        }
    }
}