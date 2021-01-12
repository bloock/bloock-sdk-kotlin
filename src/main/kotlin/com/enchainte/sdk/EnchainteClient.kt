package com.enchainte.sdk

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.message.domain.Message
import com.enchainte.sdk.message.domain.MessageReceipt
import com.enchainte.sdk.proof.domain.Proof
import com.enchainte.sdk.shared.Factory
import com.enchainte.sdk.shared.infrastructure.http.setApiKey
import io.ktor.client.*
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.rxMaybe
import kotlinx.coroutines.rx3.rxSingle

class EnchainteClient(apiKey: String) {
    private val config: ConfigService = Factory.getConfig()
    private val httpClient: HttpClient = Factory.getHttpClient()

    init {
        httpClient.setApiKey(apiKey)

        runBlocking {
            config.loadConfiguration()
        }
    }

    fun setTestEnvironment(isTest: Boolean) {
        this.config.setTestEnvironment(isTest)
    }

    fun sendMessage(message: Message): Single<Message> {
        if (!Message.isValid(message)) {
            return Single.error(Error("Invalid message"))
        }
        val messageWriteService = Factory.getMessageWriteService()
        return messageWriteService.writeMessages(message)
    }

    fun getMessages(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            val messageFindService = Factory.getMessageFindService()
            messageFindService.getMessages(messages)
        }
    }

    fun waitMessageReceipts(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            val messageWaitService = Factory.getMessageWaitService()
            messageWaitService.waitMessages(messages)
        }
    }

    fun getProof(messages: List<Message>): Maybe<Proof> {
        return rxMaybe {
            val sorted = Message.sort(messages)

            val proofService = Factory.getProofService()
            proofService.getProof(sorted)
        }
    }

    fun verifyProof(proof: Proof): Boolean {
        if (!Proof.isValid(proof)) {
            return false
        }

        return try {
            val verifyService = Factory.getVerifyService()
            val root = verifyService.verify(proof) ?: return false

            val blockchainClient = Factory.getBlockchainClient()
            blockchainClient.validateRoot(root.getHash())
        } catch (err: Throwable) {
            false
        }
    }

    fun verifyMessages(messages: List<Message>): Single<Boolean> {
        return this.getProof(messages)
            .toSingle()
            .map {
                if (it != null) {
                    this.verifyProof(it)
                } else {
                    false
                }
            }
    }
}
