package com.enchainte.sdk.service

import com.enchainte.sdk.entity.Message
import com.enchainte.sdk.entity.MessageReceipt
import com.enchainte.sdk.entity.Proof

internal class ApiService(private var apiKey: String) {

    // TODO:
    fun write(messages: Array<Message>): Boolean {
        return true
    }

    // TODO:
    fun getProof(messages: Array<Message>): Proof {
        return Proof(arrayOf(), arrayOf(), "", "")
    }

    // TODO:
    fun getMessages(messages: Array<Message>): Array<MessageReceipt> {
        return arrayOf(MessageReceipt("", "", "", "", 0))
    }
}
