package com.enchainte.sdk.entity

class MessageReceipt (
        val root: String,
        val message: String,
        val txHash: String,
        val status: String,
        val error: Int
)
