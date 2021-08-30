package com.bloock.sdk.infrastructure.http.dto

internal data class ApiResponse<T>(
    val success: Boolean?,
    val data: T?,
    val error: ApiError?
)

internal fun <T> ApiResponse<T>.isSuccess(): Boolean {
    if (this.success != null) {
        return this.success
    }
    return true
}

internal fun <T> ApiResponse<T>.getData(): T? {
    return this.data
}

internal fun <T> ApiResponse<T>.getError(): ApiError? {
    return this.error
}