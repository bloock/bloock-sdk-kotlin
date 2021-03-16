package com.enchainte.sdk

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.shared.ConfigModule
import com.enchainte.sdk.shared.InfrastructureModule
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.junit5.AutoCloseKoinTest
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import kotlin.test.assertNotNull

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
    fun testSendMessageMethod() {
        assert(true)
    }
}
