package com.enchainte.sdk.config.entity

internal class Configuration(
    var HOST: String = "",
    var WRITE_ENDPOINT: String = "",
    var PROOF_ENDPOINT: String = "",
    var FETCH_ENDPOINT: String = "",
    var FETCH_ANCHOR_ENDPOINT: String = "",
    var CONTRACT_ADDRESS: String = "",
    var CONTRACT_ABI: String = "",
    var HTTP_PROVIDER: String = "",
    var WAIT_MESSAGE_INTERVAL_FACTOR: Int = 2,
    var WAIT_MESSAGE_INTERVAL_DEFAULT: Int = 1000
)