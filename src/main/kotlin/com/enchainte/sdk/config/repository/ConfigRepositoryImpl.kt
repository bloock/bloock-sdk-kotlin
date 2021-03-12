package com.enchainte.sdk.config.repository

import com.enchainte.sdk.config.data.ConfigData
import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.entity.dto.ConfigItemResponse
import com.enchainte.sdk.config.entity.dto.exception.FetchConfigurationException
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.getAzure
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap
import kotlin.collections.set

internal class ConfigRepositoryImpl(private val httpClient: HttpClient, private val configData: ConfigData) :
    ConfigRepository {
    override suspend fun fetchConfiguration(environment: ConfigEnvironment): Configuration {
        val path = "/kv?key=SDK_%2A&label=${environment}"
        val url = "https://${configData.endpoint}$path"

        val response = httpClient.getAzure<List<ConfigItemResponse>>(url, getHeaders("GET", path))
            ?: throw FetchConfigurationException("Response is invalid")

        val resultMap = HashMap<String, String>()
        for (item in response) {
            resultMap[item.key] = item.value
        }

        try {
            configData.configuration = Configuration(
                HOST = resultMap["SDK_HOST"]!!,
                API_VERSION = resultMap["SDK_API_VERSION"]!!,
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
        } catch (e: KotlinNullPointerException) {
            throw FetchConfigurationException("Couldn't fetch all configuration parameters")
        }

        return configData.configuration
    }

    override fun getConfiguration(): Configuration {
        return configData.configuration
    }

    private fun getHeaders(method: String, url: String): HashMap<String, String> {
        val verb = method.toUpperCase()
        val dateFormatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        val utcNow = dateFormatter.format(Date())
        val contentHash = sha256("")

        val signedHeaders = "x-ms-date;host;x-ms-content-sha256"
        val stringToSign = "$verb\n$url\n$utcNow;${configData.endpoint};$contentHash"

        val hmacSigner = Mac.getInstance("HmacSHA256").apply {
            init(SecretKeySpec(Base64.getDecoder().decode(configData.secret), "HmacSHA256"))
        }
        val signature = Base64.getEncoder().encodeToString(hmacSigner.doFinal(stringToSign.toByteArray()))

        val headers = HashMap<String, String>()
        headers["x-ms-date"] = utcNow
        headers["x-ms-content-sha256"] = contentHash
        headers["Authorization"] =
            "HMAC-SHA256 Credential=${configData.credential}&SignedHeaders=${signedHeaders}&Signature=${signature}"
        return headers
    }

    private fun sha256(content: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(content.toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }
}