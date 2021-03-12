package com.enchainte.sdk.infrastructure.http.dto

internal data class AzureApiResponse<T>(
    val items: T
)