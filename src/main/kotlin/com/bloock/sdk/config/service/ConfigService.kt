package com.bloock.sdk.config.service

import com.bloock.sdk.config.entity.Configuration

internal interface ConfigService {
    fun getConfiguration(): Configuration
    fun getApiBaseUrl(): String
    fun setApiHost(host: String)
}