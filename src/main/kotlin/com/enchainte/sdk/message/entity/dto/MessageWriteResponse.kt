package com.enchainte.sdk.message.entity.dto

internal data class MessageWriteResponse(
    val anchor: Int = 0,
    val client: String = "",
    val messages: List<String> = emptyList(),
    val status: String = "Pending"
)