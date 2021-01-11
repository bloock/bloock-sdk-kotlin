package com.enchainte.sdk.message.application.retrieve.dto

import com.google.gson.annotations.SerializedName

data class MessageRetrieveResponse(
    val root: String?,
    val message: String?,
    @SerializedName("tx_hash")
    val txHash: String?,
    val status: String?,
    val error: Int?
)