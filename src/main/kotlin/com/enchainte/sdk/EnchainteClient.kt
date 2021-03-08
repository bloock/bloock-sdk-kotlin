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

/**
 * Entry-point to the Enchainté SDK
 *
 * This SDK offers all the features available in the Enchainté Toolset:
 *  * Write messages
 *  * Get messages proof
 *  * Validate proof
 *  * Get messages details
 *
 * @constructor
 * Constructor with API Key that enables accessing to Enchainté's functionalities
 *
 * @param apiKey client API Key
 */
class EnchainteClient(apiKey: String) {
    private val config: ConfigService = Factory.getConfig()
    private val httpClient: HttpClient = Factory.getHttpClient()

    init {
        httpClient.setApiKey(apiKey)

        runBlocking {
            config.loadConfiguration()
            config.setTestEnvironment(true)
        }
    }

    /**
     * Moves the current context to the Test environment
     *
     * @param isTest
     */
    fun setTestEnvironment(isTest: Boolean) {
        this.config.setTestEnvironment(isTest)
    }

    /**
     * Sends a [Message] to Enchainté
     *
     * @param message [Message] to send
     * @return RxJava [Single] that will return a [Message]
     */
    fun sendMessage(message: Message): Single<Message> {
        if (!Message.isValid(message)) {
            return Single.error(Error("Invalid message"))
        }
        val messageWriteService = Factory.getMessageWriteService()
        return messageWriteService.writeMessages(message)
    }

    /**
     * Retrieves the [MessageReceipt]s for the specified [Message]s
     *
     * @param messages to fetch
     * @return a [Single] that will return a list of [MessageReceipt]
     */
    fun getMessages(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            val messageFindService = Factory.getMessageFindService()
            messageFindService.getMessages(messages)
        }
    }

    /**
     * Waits until all specified messages are confirmed in Enchainté
     *
     * @param messages to wait for
     * @return a [Single] that will return a list of [MessageReceipt]
     */
    fun waitMessageReceipts(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            val messageWaitService = Factory.getMessageWaitService()
            messageWaitService.waitMessages(messages)
        }
    }

    /**
     * Retrieves an integrity [Proof] for the specified list of [Message]
     *
     * @param messages to validate
     * @return a [Maybe] that will return a [Proof]
     */
    fun getProof(messages: List<Message>): Maybe<Proof> {
        return rxMaybe {
            val sorted = Message.sort(messages)

            val proofService = Factory.getProofService()
            proofService.getProof(sorted)
        }
    }

    /**
     * Verifies if the specified integrity [Proof] is valid and checks if
     * it's currently included in the blockchain.
     *
     * @param proof to validate
     * @return a [Boolean] that returns true if valid, false if not
     */
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

    /**
     * It retrieves a proof for the specified list of [Message] using [getProof] and
     * verifies it using [verifyProof].
     *
     * @param messages to verify
     * @return a [Single] that will return true if valid, false if not.
     */
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
