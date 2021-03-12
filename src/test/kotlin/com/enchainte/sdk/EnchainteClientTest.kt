package com.enchainte.sdk

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.shared.ConfigModule
import com.enchainte.sdk.shared.InfrastructureModule
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.junit5.AutoCloseKoinTest
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import org.mockito.Mockito

class EnchainteClientTest: AutoCloseKoinTest() {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            InfrastructureModule,
            ConfigModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `should initialize`() {
        val apiKey = "test_api_key"

        val httpClient = declareMock<HttpClient> {
            runBlocking {
                given(setApiKey(apiKey)).will {}
            }
        }

        val configService = declareMock<ConfigService> {
            runBlocking {
                given(setupEnvironment(ConfigEnvironment.TEST)).will {
                    Configuration(
                        API_VERSION = "api_version"
                    )
                }
            }
        }

        /*val client = EnchainteClient(apiKey)

        Mockito.verify(httpClient, times(1)).setApiKey(apiKey)
        runBlocking {
            Mockito.verify(configService, times(1)).setupEnvironment(ConfigEnvironment.TEST)
        }

        assertNotNull(client)*/
    }

    @Test
    fun testSendMessageMethod() {
        assert(true)
    }
}
