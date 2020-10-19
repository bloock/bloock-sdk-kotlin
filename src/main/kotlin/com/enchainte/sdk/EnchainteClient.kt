package com.enchainte.sdk

import com.enchainte.sdk.`interface`.MessageCallback
import com.enchainte.sdk.entity.Message
import com.enchainte.sdk.entity.MessageReceipt
import com.enchainte.sdk.entity.Proof
import com.enchainte.sdk.service.*
import com.enchainte.sdk.service.ApiService
import com.enchainte.sdk.service.ConfigService
import com.enchainte.sdk.service.VerifierService
import com.enchainte.sdk.service.Web3Service
import com.enchainte.sdk.utils.Utils

class EnchainteClient(apiKey: String) {

    private val apiService: ApiService = ApiService(apiKey)
    private val configService: ConfigService = ConfigService()
    private val verifierService: VerifierService = VerifierService()
    private val web3Service: Web3Service = Web3Service()

    fun setTestEnvironment(isTest: Boolean) {
        this.configService.setTestEnvironment(isTest)
    }

    fun sendMessage(message: Message, callback: MessageCallback) {
        if (!Message.isValid(message)) {
            return callback.onMessageError("Invalid message")
        }
        return WriterService.push(message)
    }

    fun getMessages(messages: Array<Message>): Array<MessageReceipt> {
        return this.apiService.getMessages(messages)
    }

    fun waitMessageReceipts(messages: Array<Message>): Array<MessageReceipt> {
        var completed: Boolean
        var attempts = 0
        var messageReceipts: Array<MessageReceipt>

        do {
            messageReceipts = this.getMessages(messages)
            completed = messageReceipts.all{receipt -> receipt.status === "success" || receipt.status === "error"}

            if (completed) break

            Utils.sleep(
                configService.getConfiguration().WAIT_MESSAGE_INTERVAL_DEFAULT +
                    attempts * configService.getConfiguration().WAIT_MESSAGE_INTERVAL_FACTOR
            )
            attempts += 1
        } while (!completed)

        return messageReceipts
    }

    fun getProof(messages: Array<Message>): Proof {
        val sorted = Message.sort(messages)
        return apiService.getProof(sorted)
    }

    fun verifyProof(proof: Proof): Boolean {
        if (!Proof.isValid(proof)) {
            return false
        }

        try {
            val valid = verifierService.verify(proof)
            if (!valid) {
                return false
            }
            return web3Service.validateRoot("root")
        } catch (err: Exception) {
            return false
        }
    }

    fun verifyMessages(messages: Array<Message>): Boolean {
        val proof = this.getProof(messages)
        return this.verifyProof(proof)
    }
}
