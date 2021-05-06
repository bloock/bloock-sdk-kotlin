package com.enchainte.sdk.message.entity.dto

internal data class MessageRetrieveResponse(
    val anchor: Int = 0,
    val client: String = "",
    val message: String = "",
    val status: String = "Pending"
)