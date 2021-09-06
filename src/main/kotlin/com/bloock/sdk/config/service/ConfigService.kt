package com.bloock.sdk.config.service

import com.bloock.sdk.config.entity.Configuration
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.config.entity.NetworkConfiguration

internal interface ConfigService {
    fun getConfiguration(): Configuration
    fun getApiBaseUrl(): String
    fun setApiHost(host: String)
    fun getNetworkConfiguration(network: Network): NetworkConfiguration
    fun setNetworkConfiguration(network: Network, config: NetworkConfiguration)
}