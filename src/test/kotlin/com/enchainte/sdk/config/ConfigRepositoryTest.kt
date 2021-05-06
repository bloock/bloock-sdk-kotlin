package com.enchainte.sdk.config

import com.enchainte.sdk.config.data.ConfigData
import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.config.repository.ConfigRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ConfigRepositoryTest {

    @Test
    internal fun test_fetch_configuration_prod() {
        val configData = mockk<ConfigData>()
        every { configData.setConfiguration() } returns Configuration()

        val configRepository: ConfigRepository = ConfigRepositoryImpl(configData)
        configRepository.fetchConfiguration(ConfigEnvironment.PROD)

        verify(exactly = 1) { configData.setConfiguration() }
    }

    @Test
    internal fun test_fetch_configuration_test() {
        val configData = mockk<ConfigData>()
        every { configData.setTestConfiguration() } returns Configuration()

        val configRepository: ConfigRepository = ConfigRepositoryImpl(configData)
        configRepository.fetchConfiguration(ConfigEnvironment.TEST)

        verify(exactly = 1) { configData.setTestConfiguration() }
    }

}
