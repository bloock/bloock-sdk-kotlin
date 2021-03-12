package com.enchainte.sdk.config.data

import com.enchainte.sdk.config.entity.Configuration

internal data class ConfigData(
    val endpoint: String = "enchainte-config.azconfig.io",
    val credential: String = "ihs8-l9-s0:JPRPUeiXJGsAzFiW9WDc",
    val secret: String = "1UA2dijC0SIVyrPKUKG0gT0oXxkVaMrUfJuXkLr+i0c=",
    var configuration: Configuration = Configuration()
) {

}