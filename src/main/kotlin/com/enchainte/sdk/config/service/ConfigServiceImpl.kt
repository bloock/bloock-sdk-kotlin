package com.enchainte.sdk.config.service

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.repository.ConfigRepository

internal class ConfigServiceImpl(private val configRepository: ConfigRepository) : ConfigService {
    override fun setupEnvironment(environment: ConfigEnvironment): Configuration {
        return configRepository.fetchConfiguration(environment)
    }

    override fun getConfiguration(): Configuration {
        return configRepository.getConfiguration()
    }

    override fun getApiBaseUrl(): String {
        return this.configRepository.getConfiguration().HOST
    }
}