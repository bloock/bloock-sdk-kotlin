package com.bloock.sdk.config.repository

import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.config.entity.Configuration
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.config.entity.NetworkConfiguration

internal class ConfigRepositoryImpl(private val configData: ConfigData) : ConfigRepository {

    override fun getConfiguration(): Configuration {
        return configData.configuration
    }

    override fun setApiHost(host: String) {
        this.configData.configuration.HOST = host
    }

    override fun getNetworkConfiguration(network: Network): NetworkConfiguration {
        return this.configData.getNetworkConfiguration(network)
    }

    override fun setNetworkConfiguration(network: Network, config: NetworkConfiguration) {
        this.configData.setNetworkConfiguration(network, config)
    }
}