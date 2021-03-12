package com.enchainte.sdk.infrastructure.http

import com.enchainte.sdk.config.entity.dto.exception.FetchConfigurationException
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.http.dto.*
import com.enchainte.sdk.infrastructure.http.dto.exception.ApiErrorException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.reflect.Type
import kotlin.reflect.KClass
import io.ktor.client.HttpClient as KtorHttpClient


internal class HttpClientImpl(private val client: KtorHttpClient, private val httpClientData: HttpClientData): HttpClient {

    override fun setApiKey(apiKey: String) {
        httpClientData.apiKey = apiKey
    }

    override suspend fun <T: Any> get(url: String, headers: HashMap<String, String>, type: Type): T? {
        try {
            val response = client.get<ApiResponse<Any>> {
                url(url)
                contentType(ContentType.Application.Json)

                if (!headers.containsKey("Authorization")) {
                    header("Authorization", getAuthenticationHeader())
                }

                for (header in headers) {
                    header(header.key, header.value)
                }
            }

            if (response.isError()) {
                throw ApiErrorException(response.getError())
            }

            val rawData = Gson().toJson(response.getData())
            return Gson().fromJson(rawData, type)
        } catch (t: Throwable) {
            throw ApiErrorException(ApiError(t.message ?: "ApiErrorException", 0))
        }
    }

    override suspend fun <T: Any> post(url: String, _body: Any, headers: HashMap<String, String>, type: Type): T? {
        try {
            val response = client.post<ApiResponse<Any>> {
                url(url)
                contentType(ContentType.Application.Json)
                body = _body

                if (!headers.containsKey("Authorization")) {
                    header("Authorization", getAuthenticationHeader())
                }

                for (header in headers) {
                    header(header.key, header.value)
                }
            }

            if (response.isError()) {
                throw ApiErrorException(response.getError())
            }

            val rawData = Gson().toJson(response.getData())
            return Gson().fromJson(rawData, type)
        } catch (t: Throwable) {
            throw ApiErrorException(ApiError(t.message ?: "ApiErrorException", 0))
        }
    }

    override suspend fun <T: Any> getAzure(url: String, headers: HashMap<String, String>, type: Type): T? {
        try {
            val response = client.get<AzureApiResponse<Any>> {
                url(url)
                contentType(ContentType.Application.Json)

                if (!headers.containsKey("Authorization")) {
                    header("Authorization", getAuthenticationHeader())
                }

                for (header in headers) {
                    header(header.key, header.value)
                }
            }

            val rawData = Gson().toJson(response.items)
            return Gson().fromJson(rawData, type)
        } catch (t: Throwable) {
            throw FetchConfigurationException("Exception while fetching Azure")
        }
    }

    private fun getAuthenticationHeader(): String {
        return "Bearer ${httpClientData.apiKey}"
    }
}
