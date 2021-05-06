package com.enchainte.sdk.config.repository

import com.enchainte.sdk.config.data.ConfigData
import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration

internal class ConfigRepositoryImpl(private val configData: ConfigData) : ConfigRepository {
    override fun fetchConfiguration(environment: ConfigEnvironment): Configuration {
        return when(environment) {
            ConfigEnvironment.PROD -> this.configData.setConfiguration()
            ConfigEnvironment.TEST -> this.configData.setTestConfiguration()
        }
    }

    override fun getConfiguration(): Configuration {
        return configData.configuration
    }
}