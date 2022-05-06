package com.bloock.sdk.config.entity

internal class Configuration(
    var HOST: String = "",
    var WAIT_MESSAGE_INTERVAL_FACTOR: Int = 2,
    var WAIT_MESSAGE_INTERVAL_DEFAULT: Int = 1000,
    var KEY_TYPE_ALGORITHM: String = "EC",
    var ELLIPTIC_CURVE_KEY: String = "secp256k1",
    var SIGNATURE_ALGORITHM: String = "ES256K"
) {
}

class NetworkConfiguration(
    var CONTRACT_ADDRESS: String = "",
    var CONTRACT_ABI: String = "",
    var HTTP_PROVIDER: String = "",
)