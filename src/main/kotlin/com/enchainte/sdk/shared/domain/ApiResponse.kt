package com.enchainte.sdk.shared.domain

internal data class ApiResponse<T> (
    val success: Boolean,
    val data: T?,
    val error: ApiError?
)

internal fun <T> ApiResponse<T>.isError(): Boolean {
    return this.success
}

internal fun <T> ApiResponse<T>.getData(): T? {
    return this.data
}

internal fun <T> ApiResponse<T>.getError(): ApiError? {
    return this.error
}