package com.enchainte.sdk.shared

import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.message.service.MessageService
import com.enchainte.sdk.proof.service.ProofService
import org.junit.jupiter.api.Test
import org.koin.core.component.inject
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertNotNull

class DependencyInjectionTest: AutoCloseKoinTest() {
    @Test
    fun `check DI hierarchy`() {
        checkModules {
            modules(InfrastructureModule)
            modules(ConfigModule)
            modules(MessageModule)
            modules(ProofModule)
        }

        setUpDependencyInjection()

        val configService by inject<ConfigService>()
        assertNotNull(configService)

        val messageService by inject<MessageService>()
        assertNotNull(messageService)

        val proofService by inject<ProofService>()
        assertNotNull(proofService)

        val httpClient by inject<HttpClient>()
        assertNotNull(httpClient)
    }
}