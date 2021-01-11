package com.enchainte.sdk.config.domain.dto

data class ConfigResponse(val items: List<ConfigItemResponse>)

data class ConfigItemResponse(val key: String, val value: String)