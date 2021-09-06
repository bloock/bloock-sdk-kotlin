package com.bloock.sdk.config.entity

internal class Configuration(
    var HOST: String = "",
    var WAIT_MESSAGE_INTERVAL_FACTOR: Int = 2,
    var WAIT_MESSAGE_INTERVAL_DEFAULT: Int = 1000
)

class NetworkConfiguration(
    var CONTRACT_ADDRESS: String = "",
    var CONTRACT_ABI: String = "",
    var HTTP_PROVIDER: String = "",
)