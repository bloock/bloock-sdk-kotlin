package com.bloock.sdk.record.entity.dto

internal data class RecordWriteResponse(
    val anchor: Int = 0,
    val client: String = "",
    val messages: List<String> = emptyList(),
    val status: String = "Pending"
)