package com.bloock.sdk.config

import com.bloock.sdk.config.entity.Configuration
import com.bloock.sdk.config.repository.ConfigRepository
import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.config.service.ConfigServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConfigServiceTest {

    @Test
    internal fun test_set_host() {
        val modifiedHost = "https://modified.bloock.com"

        val configRepository = mockk<ConfigRepository>()
        every { configRepository.setApiHost(modifiedHost) } returns Unit

        val configService: ConfigService = ConfigServiceImpl(configRepository)

        configService.setApiHost(modifiedHost)

        verify(exactly = 1) { configRepository.setApiHost(modifiedHost) }
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
