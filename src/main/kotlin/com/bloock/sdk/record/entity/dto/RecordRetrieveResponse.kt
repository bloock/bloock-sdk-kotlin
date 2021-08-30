package com.bloock.sdk.record.entity.dto

internal data class RecordRetrieveResponse(
    val anchor: Int = 0,
    val client: String = "",
    val message: String = "",
    val status: String = "Pending"
)