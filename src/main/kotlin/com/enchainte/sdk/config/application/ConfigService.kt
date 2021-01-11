package com.enchainte.sdk.config.application

import com.enchainte.sdk.config.domain.Configuration
import com.enchainte.sdk.config.domain.dto.ConfigResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal class ConfigService(private val httpClient: HttpClient) {
    private var environment: String = "PROD"

    private var configuration: Configuration = Configuration()

    object ConfigConstants {
        const val ENDPOINT = "enchainte-config.azconfig.io"
        const val CREDENTIAL = "ihs8-l9-s0:JPRPUeiXJGsAzFiW9WDc"
        const val SECRET = "1UA2dijC0SIVyrPKUKG0gT0oXxkVaMrUfJuXkLr+i0c="
    }

    fun setTestEnvironment(isTest: Boolean) {
        if (isTest) this.environment = "TEST"
        else this.environment = "PROD"

        runBlocking {
            loadConfiguration()
        }
    }

    fun getConfiguration(): Configuration {
        return this.configuration
    }

    suspend fun loadConfiguration(): Configuration {
        try {
            val path = "/kv?key=SDK_%2A&label=${this.environment}"
            val url = "https://${ConfigConstants.ENDPOINT}$path"

            val response = httpClient.get<ConfigResponse> {
                url(url)
                headers(getHeaders("GET", path))
            }

            val resultMap = HashMap<String, String>()
            val itemList = response.items
            for (item in itemList) {
                resultMap[item.key] = item.value
            }

            this.configuration =  Configuration(
                HOST = resultMap["SDK_HOST"]!!,
                WRITE_ENDPOINT = resultMap["SDK_WRITE_ENDPOINT"]!!,
                PROOF_ENDPOINT = resultMap["SDK_PROOF_ENDPOINT"]!!,
                FETCH_ENDPOINT = resultMap["SDK_FETCH_ENDPOINT"]!!,
                CONTRACT_ADDRESS = resultMap["SDK_CONTRACT_ADDRESS"]!!,
                CONTRACT_ABI = resultMap["SDK_CONTRACT_ABI"]!!,
                PROVIDER = resultMap["SDK_PROVIDER"]!!,
                HTTP_PROVIDER = resultMap["SDK_HTTP_PROVIDER"]!!,
                WRITE_INTERVAL = resultMap["SDK_WRITE_INTERVAL"]!!.toInt(),
                CONFIG_INTERVAL = resultMap["SDK_CONFIG_INTERVAL"]!!.toInt(),
                WAIT_MESSAGE_INTERVAL_FACTOR = resultMap["SDK_WAIT_MESSAGE_INTERVAL_FACTOR"]!!.toInt(),
                WAIT_MESSAGE_INTERVAL_DEFAULT = resultMap["SDK_WAIT_MESSAGE_INTERVAL_DEFAULT"]!!.toInt()
            )

            return this.configuration
        } catch (err: Exception) {
            throw Exception(
                "Error contacting App Configuration Service",
                err
            )
        }
    }

    private fun getHeaders(method: String, url: String): HeadersBuilder.() -> Unit {
        val verb = method.toUpperCase()
        val dateFormatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        val utcNow = dateFormatter.format(Date())
        val contentHash = sha256("")

        val signedHeaders = "x-ms-date;host;x-ms-content-sha256"
        val stringToSign = "$verb\n$url\n$utcNow;${ConfigConstants.ENDPOINT};$contentHash"

        val hmacSigner = Mac.getInstance("HmacSHA256").apply {
            init(SecretKeySpec(Base64.getDecoder().decode(ConfigConstants.SECRET), "HmacSHA256"))
        }
        val signature = Base64.getEncoder().encodeToString(hmacSigner.doFinal(stringToSign.toByteArray()))

        return {
            set("x-ms-date", utcNow)
            set("x-ms-content-sha256", contentHash)
            set("Authorization", "HMAC-SHA256 Credential=${ConfigConstants.CREDENTIAL}&SignedHeaders=${signedHeaders}&Signature=${signature}")
        }
    }

    private fun sha256(content: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(content.toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }

}