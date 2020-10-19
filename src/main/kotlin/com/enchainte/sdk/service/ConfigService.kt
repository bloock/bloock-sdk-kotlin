package com.enchainte.sdk.service

import com.enchainte.sdk.entity.Configuration
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.core.Headers
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.json.JSONObject

internal class ConfigService {

    private var ENVIRONMENT: String = "PROD";

    private var configuration: Configuration = Configuration();

    object ConfigConstants {
        const val ENDPOINT = "enchainte-config.azconfig.io";
        const val CREDENTIAL = "ihs8-l9-s0:JPRPUeiXJGsAzFiW9WDc";
        const val SECRET = "1UA2dijC0SIVyrPKUKG0gT0oXxkVaMrUfJuXkLr+i0c=";
    }

    init {
        this.configuration = readConfiguration()
    }

    public fun setTestEnvironment(isTest: Boolean) {
        if (isTest) this.ENVIRONMENT = "TEST";
        else this.ENVIRONMENT = "PROD";

        this.readConfiguration();
    }

    public fun getConfiguration(): Configuration {
        return this.configuration;
    }

    private fun readConfiguration(): Configuration {
        try {
            val path = "/kv?key=SDK_*&label=${this.ENVIRONMENT}"
            val headers = authorization("GET", path)

            val (_, response, result) = Fuel.get("https://${ConfigConstants.ENDPOINT}$path").header(headers).responseString()
            val (payload, error) = result
            if (response.statusCode != 200 || error != null) {
                throw Exception("Bad response from Azure App Configuration service")
            }
            
            val jsonObject = JSONObject(payload)
            val itemArray = jsonObject.getJSONArray("items")
            val resultMap = HashMap<String, String>()
            for (i in 0 until itemArray.length()) {
                val item = itemArray.getJSONObject(i)
                resultMap[item.getString("key")] = item.getString("value")
            }

            return Configuration(
                    HOST = resultMap.get("SDK_HOST")!!,
                    WRITE_ENDPOINT = resultMap.get("SDK_WRITE_ENDPOINT")!!,
                    PROOF_ENDPOINT = resultMap.get("SDK_PROOF_ENDPOINT")!!,
                    FETCH_ENDPOINT = resultMap.get("SDK_FETCH_ENDPOINT")!!,
                    CONTRACT_ADDRESS = resultMap.get("SDK_CONTRACT_ADDRESS")!!,
                    CONTRACT_ABI = resultMap.get("SDK_CONTRACT_ABI")!!,
                    PROVIDER = resultMap.get("SDK_PROVIDER")!!,
                    WRITE_INTERVAL = resultMap.get("SDK_WRITE_INTERVAL")!!.toInt(),
                    CONFIG_INTERVAL = resultMap.get("SDK_CONFIG_INTERVAL")!!.toInt(),
                    WAIT_MESSAGE_INTERVAL_FACTOR = resultMap.get("SDK_WAIT_MESSAGE_INTERVAL_FACTOR")!!.toInt(),
                    WAIT_MESSAGE_INTERVAL_DEFAULT = resultMap.get("SDK_WAIT_MESSAGE_INTERVAL_DEFAULT")!!.toInt()
            )
        } catch (err: Exception) {
            throw Exception(
                "Error contacting App Configuration Service",
                err
            )
        }
    }

    private fun authorization(method: String, url: String): Headers {
        val verb = method.toUpperCase();
        val dateFormatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        val utcNow = dateFormatter.format(Date())
        val contentHash = sha256("")

        val signedHeaders = "x-ms-date;host;x-ms-content-sha256";
        val stringToSign = "$verb\n$url\n$utcNow;${ConfigConstants.ENDPOINT};$contentHash";

        val hmacSigner = Mac.getInstance("HmacSHA256").apply {
            init(SecretKeySpec(Base64.getDecoder().decode(ConfigConstants.SECRET), "HmacSHA256"))
        }
        val signature = Base64.getEncoder().encodeToString(hmacSigner.doFinal(stringToSign.toByteArray()))

        return Headers.from(
            Pair("x-ms-date", utcNow),
            Pair("x-ms-content-sha256", contentHash),
            Pair("Authorization", "HMAC-SHA256 Credential=${ConfigConstants.CREDENTIAL}&SignedHeaders=${signedHeaders}&Signature=${signature}")
        )
    }

    private fun sha256(content: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(content.toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }
}
