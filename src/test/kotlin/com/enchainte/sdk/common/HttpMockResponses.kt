package com.enchainte.sdk.common

import com.enchainte.sdk.config.domain.dto.ConfigItemResponse
import com.enchainte.sdk.config.domain.dto.ConfigResponse
import com.google.gson.Gson

internal class HttpMockResponses {
    companion object {
        fun getResponse(host: String, path: String): String {
            val gson = Gson()
            return when (host) {
                "enchainte-config.azconfig.io" -> {
                    val config = ConfigResponse(
                        items = listOf(
                            ConfigItemResponse("HOST", "https://api.enchainte.com"),
                            ConfigItemResponse("WRITE_ENDPOINT", "/write"),
                            ConfigItemResponse("PROOF_ENDPOINT", "/proof"),
                            ConfigItemResponse("FETCH_ENDPOINT", "/message/fetch"),
                            ConfigItemResponse("CONTRACT_ADDRESS", ""),
                            ConfigItemResponse("CONTRACT_ABI", ""),
                            ConfigItemResponse("PROVIDER", ""),
                            ConfigItemResponse("HTTP_PROVIDER", ""),
                            ConfigItemResponse("WRITE_INTERVAL", "100"),
                            ConfigItemResponse("CONFIG_INTERVAL", "3600"),
                            ConfigItemResponse("WAIT_MESSAGE_INTERVAL_FACTOR", "1.5"),
                            ConfigItemResponse("WAIT_MESSAGE_INTERVAL_DEFAULT", "1000")
                        )
                    )
                    gson.toJson(config)
                }
                else -> when (path) {
                    "/" -> {
                        "Hello, world"
                    }
                    else -> {
                        throw Error("Not mocked request")
                    }
                }
            }
        }
    }
}