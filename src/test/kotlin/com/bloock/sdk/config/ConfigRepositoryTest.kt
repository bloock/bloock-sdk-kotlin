package com.bloock.sdk.config

import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.config.repository.ConfigRepository
import com.bloock.sdk.config.repository.ConfigRepositoryImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConfigRepositoryTest {

    @Test
    internal fun test_fetch_configuration_prod() {
        val configData = ConfigData()

        val configRepository: ConfigRepository = ConfigRepositoryImpl(configData)
        configRepository.setApiHost("https://modified.bloock.com")

        assertEquals("https://modified.bloock.com", configData.configuration.HOST)
    }
}
