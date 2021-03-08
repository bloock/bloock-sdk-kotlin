package com.enchainte.sdk.config.domain

internal class Configuration (
    val HOST: String = "",
    val API_VERSION: String = "",
    val WRITE_ENDPOINT: String = "",
    val PROOF_ENDPOINT: String = "",
    val FETCH_ENDPOINT: String = "",
    val CONTRACT_ADDRESS: String = "",
    val CONTRACT_ABI: String = "",
    val PROVIDER: String = "",
    val HTTP_PROVIDER: String = "",
    val WRITE_INTERVAL: Int = 1000,
    val CONFIG_INTERVAL: Int = 10000,
    val WAIT_MESSAGE_INTERVAL_FACTOR: Int = 2,
    val WAIT_MESSAGE_INTERVAL_DEFAULT: Int = 1000
)