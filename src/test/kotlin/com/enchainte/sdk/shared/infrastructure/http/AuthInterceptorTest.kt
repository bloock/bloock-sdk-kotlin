package com.enchainte.sdk.shared.infrastructure.http

import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.common.BaseTest
import com.enchainte.sdk.shared.Factory
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AuthInterceptorTest: BaseTest() {
    @Test
    fun testAuthHttpRequest() = runBlocking {
        val apiKey = "123456789abcdef"

        val httpClient = loadMockHttpEngine(apiKey)
        Factory.getConfig().loadConfiguration()
        val response = httpClient.get<HttpResponse>("https://api.enchainte.com/")

        assertNotNull(response)
        assertEquals(
            "Bearer $apiKey",
            response.request.headers[HttpHeaders.Authorization]
        )
    }
}