package com.enchainte.sdk.message.domain

class MessageReceipt (
        val root: String,
        val message: String,
        val txHash: String,
        val status: String,
        val error: Int
)
