package com.enchainte.sdk.config.service

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration

internal interface ConfigService {
    fun setupEnvironment(environment: ConfigEnvironment): Configuration
    fun getConfiguration(): Configuration
    fun getApiBaseUrl(): String
}