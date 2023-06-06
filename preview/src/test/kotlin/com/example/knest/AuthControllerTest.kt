package com.example.knest

import com.example.knest.dtos.TestBodyDto
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import io.ktor.server.testing.*
import org.junit.Test
import java.nio.charset.Charset
import java.util.Base64
import kotlin.test.assertEquals

class AuthControllerTest {

    private val id: Int = 42
    private val name: String = "test"
    private val password: String = "123"
    private val testBody = TestBodyDto(id, "John")

    @Test
    fun testBasicAuth() = testApplication {
        val token = Base64
            .getEncoder()
            .encodeToString("$name:$password".toByteArray(Charset.defaultCharset()))

        val response: String = client
            .get("/auth/basic") { header("Authorization", "Basic $token") }
            .bodyAsText()

        assertEquals(name, response, "Basic auth should work correctly")
    }

    @Test
    fun testJwtAuth() = testApplication {
        val httpClient = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val token: String = httpClient
            .post("/auth") {
                header("content-type", "application/json")
                setBody(testBody)
            }
            .bodyAsText()

        val response: String = client
            .get("/auth/jwt") { header("Authorization", "Bearer $token") }
            .bodyAsText()

        assertEquals(id.toString(), response, "JWT auth should work correctly")
    }
}