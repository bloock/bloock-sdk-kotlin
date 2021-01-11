package com.enchainte.sdk.shared.infrastructure.http

import com.enchainte.sdk.shared.Factory
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.util.*

internal class BearerConfig {
    lateinit var apiKey: String
}

internal class BearerProvider (
    internal var apiKey: String = "",
    override val sendWithoutRequest: Boolean = true
) : AuthProvider {
    override fun isApplicable(auth: HttpAuthHeader): Boolean {
        return true
    }

    override suspend fun addRequestHeaders(request: HttpRequestBuilder) {
        val config = Factory.getConfig()
        val host = "${request.url.protocol.name}://${request.url.host}"

        if (host == config.getConfiguration().HOST) {
            request.headers[HttpHeaders.Authorization] = constructBearerAuthValue()
        }
    }

    private fun constructBearerAuthValue(): String {
        return "Bearer $apiKey"
    }
}

internal fun Auth.bearer(block: BearerConfig.() -> Unit) {
    with(BearerConfig().apply(block)) {
        providers.add(BearerProvider(apiKey))
    }
}