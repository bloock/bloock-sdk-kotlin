package com.enchainte.sdk.message.entity.dto

internal data class MessageRetrieveResponse(
    val anchor: Int?,
    val client: String?,
    val message: String?,
    val status: String?
)