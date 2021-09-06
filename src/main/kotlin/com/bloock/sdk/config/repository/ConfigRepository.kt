package com.bloock.sdk.config.repository

import com.bloock.sdk.config.entity.Configuration
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.config.entity.NetworkConfiguration

internal interface ConfigRepository {
    fun getConfiguration(): Configuration
    fun setApiHost(host: String)
    fun getNetworkConfiguration(network: Network): NetworkConfiguration
    fun setNetworkConfiguration(network: Network, config: NetworkConfiguration)
}