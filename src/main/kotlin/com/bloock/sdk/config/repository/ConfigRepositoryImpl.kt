package com.bloock.sdk.config.repository

import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.config.entity.Configuration

internal class ConfigRepositoryImpl(private val configData: ConfigData) : ConfigRepository {

    override fun getConfiguration(): Configuration {
        return configData.configuration
    }

    override fun setApiHost(host: String) {
        this.configData.configuration.HOST = host
    }
}