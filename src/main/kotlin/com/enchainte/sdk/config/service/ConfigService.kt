package com.enchainte.sdk.config.service

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration

internal interface ConfigService {
    suspend fun setupEnvironment(environment: ConfigEnvironment): Configuration
    fun getConfiguration(): Configuration
}