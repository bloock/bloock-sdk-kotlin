package com.enchainte.sdk.message.entity.dto

internal data class MessageWriteResponse(
    val anchor: Int?,
    val client: String?,
    val messages: List<String>?,
    val status: String?
)