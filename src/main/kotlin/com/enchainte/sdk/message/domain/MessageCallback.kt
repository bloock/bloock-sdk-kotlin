package com.enchainte.sdk.message.domain

interface MessageCallback {
    fun onMessageSuccess()
    fun onMessageError(message: String)
}