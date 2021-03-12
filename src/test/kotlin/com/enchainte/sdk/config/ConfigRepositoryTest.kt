package com.enchainte.sdk.config

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.dto.ConfigItemResponse
import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.http.HttpClientData
import com.enchainte.sdk.infrastructure.http.HttpClientImpl
import com.enchainte.sdk.shared.ConfigModule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ConfigRepositoryTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single {
                    HttpClient(CIO) {
                        install(JsonFeature) {
                            serializer = GsonSerializer()
                        }
                    }
                }
                single { HttpClientData() }
                single { HttpClientImpl(get(), get()) as HttpClient }
            },
            ConfigModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun testRetrieveConfig() = runBlocking {
        val url = "https://enchainte-config.azconfig.io/kv?key=SDK_%2A&label=TEST"
        val headers = HashMap<String, String>()
        headers["x-ms-date"] = "Wed, 17 Feb 2021 15:20:08 GMT"
        headers["x-ms-content-sha256"] = "47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU="
        headers["Authorization"] =
            "HMAC-SHA256 Credential=ihs8-l9-s0:JPRPUeiXJGsAzFiW9WDc&SignedHeaders=x-ms-date;host;x-ms-content-sha256&Signature=o+pP83fes3/94OHjHWYYEKUV7D0DoE3lLoqsLofRWNQ="

        val httpClient = declareMock<HttpClient> {
            runBlocking {
                given(getAzure<List<ConfigItemResponse>>(eq(url), any(), any())).will {
                    listOf(
                        ConfigItemResponse("SDK_HOST", "SDK_HOST"),
                        ConfigItemResponse("SDK_API_VERSION", "SDK_API_VERSION"),
                        ConfigItemResponse("SDK_WRITE_ENDPOINT", "SDK_WRITE_ENDPOINT"),
                        ConfigItemResponse("SDK_PROOF_ENDPOINT", "SDK_PROOF_ENDPOINT"),
                        ConfigItemResponse("SDK_FETCH_ENDPOINT", "SDK_FETCH_ENDPOINT"),
                        ConfigItemResponse("SDK_CONTRACT_ADDRESS", "SDK_CONTRACT_ADDRESS"),
                        ConfigItemResponse("SDK_CONTRACT_ABI", "SDK_CONTRACT_ABI"),
                        ConfigItemResponse("SDK_PROVIDER", "SDK_PROVIDER"),
                        ConfigItemResponse("SDK_HTTP_PROVIDER", "SDK_HTTP_PROVIDER"),
                        ConfigItemResponse("SDK_WRITE_INTERVAL", "1"),
                        ConfigItemResponse("SDK_CONFIG_INTERVAL", "1"),
                        ConfigItemResponse("SDK_WAIT_MESSAGE_INTERVAL_FACTOR", "1"),
                        ConfigItemResponse("SDK_WAIT_MESSAGE_INTERVAL_DEFAULT", "1")
                    )
                }
            }
        }

        val configRepository: ConfigRepository = get()
        val config = configRepository.fetchConfiguration(ConfigEnvironment.TEST)

        Mockito.verify(httpClient, times(1)).getAzure<List<ConfigItemResponse>>(eq(url), any(), any())
        assertNotNull(config, "config is null")
        assertEquals(config.API_VERSION, "SDK_API_VERSION")
    }
}
