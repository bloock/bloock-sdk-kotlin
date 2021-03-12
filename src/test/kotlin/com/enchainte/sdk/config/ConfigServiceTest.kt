package com.enchainte.sdk.config

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.entity.dto.ConfigItemResponse
import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.http.HttpClientData
import com.enchainte.sdk.shared.ConfigModule
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

class ConfigServiceTest: KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            ConfigModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `should fetch configuration`() {
        val apiVersion = "/v1"
        val configRepository = declareMock<ConfigRepository> {
            runBlocking {
                given(fetchConfiguration(ConfigEnvironment.TEST)).will {
                    Configuration(
                        API_VERSION = apiVersion
                    )
                }
            }
        }

        val configService: ConfigService = get()
        runBlocking {
            val config = configService.setupEnvironment(ConfigEnvironment.TEST)

            Mockito.verify(configRepository, times(1)).fetchConfiguration(ConfigEnvironment.TEST)
            assertEquals(config.API_VERSION, apiVersion)
        }
    }
}