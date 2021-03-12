package com.enchainte.sdk.config.entity.dto

internal data class ConfigResponse(
    val items: List<ConfigItemResponse>
)

internal data class ConfigItemResponse(val key: String, val value: String)