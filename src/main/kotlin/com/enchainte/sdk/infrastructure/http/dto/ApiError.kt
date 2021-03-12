package com.enchainte.sdk.infrastructure.http.dto

internal data class ApiError(
    val message: String,
    val status: Int
)