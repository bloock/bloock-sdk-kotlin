package com.enchainte.sdk.config

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.config.service.ConfigServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConfigServiceTest {

    @Test
    internal fun test_setup_environment() {
        val configRepository = mockk<ConfigRepository>()
        every { configRepository.fetchConfiguration(ConfigEnvironment.TEST) } returns Configuration()

        val configService: ConfigService = ConfigServiceImpl(configRepository)
        configService.setupEnvironment(ConfigEnvironment.TEST)

        verify(exactly = 1) { configRepository.fetchConfiguration(ConfigEnvironment.TEST) }
    }

    @Test
    internal fun test_get_configuration() {
        val configRepository = mockk<ConfigRepository>()
        every { configRepository.getConfiguration() } returns Configuration()

        val configService: ConfigService = ConfigServiceImpl(configRepository)

        configService.getConfiguration()

        verify(exactly = 1) { configRepository.getConfiguration() }
    }

    @Test
    internal fun test_get_base_url() {
        val configuration = Configuration()
        configuration.HOST = "test"

        val configRepository = mockk<ConfigRepository>()
        every { configRepository.getConfiguration() } returns configuration

        val configService: ConfigService = ConfigServiceImpl(configRepository)

        val apiUrl = configService.getApiBaseUrl()

        assertEquals(apiUrl, "test")
    }
}
