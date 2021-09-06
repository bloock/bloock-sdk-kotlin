package com.bloock.sdk.config.service

import com.bloock.sdk.config.entity.Configuration
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.config.entity.NetworkConfiguration
import com.bloock.sdk.config.repository.ConfigRepository

internal class ConfigServiceImpl(private val configRepository: ConfigRepository) : ConfigService {
    override fun getConfiguration(): Configuration {
        return configRepository.getConfiguration()
    }

    override fun getApiBaseUrl(): String {
        return this.configRepository.getConfiguration().HOST
    }

    override fun setApiHost(host: String) {
        this.configRepository.setApiHost(host)
    }

    override fun getNetworkConfiguration(network: Network): NetworkConfiguration {
        return this.configRepository.getNetworkConfiguration(network)
    }

    override fun setNetworkConfiguration(network: Network, config: NetworkConfiguration) {
        this.configRepository.setNetworkConfiguration(network, config)
    }
}