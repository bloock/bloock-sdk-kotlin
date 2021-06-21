package com.enchainte.sdk

import com.enchainte.sdk.anchor.entity.Anchor
import com.enchainte.sdk.anchor.service.AnchorService
import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.MessageReceipt
import com.enchainte.sdk.message.service.MessageService
import com.enchainte.sdk.proof.entity.Proof
import com.enchainte.sdk.proof.service.ProofService
import com.enchainte.sdk.shared.DependencyInjection
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
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
 * @param environment (optional) defines the Enchainté's environment to use. By default: production
 */
class EnchainteClient(private val apiKey: String, private val environment: ConfigEnvironment) {

    constructor(apiKey: String) : this(apiKey, ConfigEnvironment.PROD)

    private var anchorService: AnchorService = DependencyInjection.getAnchorService()
    private var configService: ConfigService = DependencyInjection.getConfigService()
    private var messageService: MessageService = DependencyInjection.getMessageService()
    private var proofService: ProofService = DependencyInjection.getProofService()

    private var httpClient: HttpClient = DependencyInjection.getHttpClient()

    init {
        httpClient.setApiKey(apiKey)
        configService.setupEnvironment(environment)
    }

    /**
     * Sends a list of [Message] to Enchainté
     *
     * @param messages list of [Message] to send
     * @return RxJava [Single] that will return a list of [MessageReceipt]
     * @throws [InvalidMessageException] At least one of the messages sent was not well formed.
     * @throws [HttpRequestException] Error returned by Enchainté's API.
     */
    fun sendMessages(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            messageService.sendMessages(messages)
        }
    }

    /**
     * Retrieves all [MessageReceipt]s for the specified [Anchor]s
     *
     * @param messages to fetch
     * @return a [Single] that will return a list of [MessageReceipt]
     * @throws [InvalidMessageException] At least one of the messages sent was not well formed.
     * @throws [HttpRequestException] Error returned by Enchainté's API.
     */
    fun getMessages(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            messageService.getMessages(messages)
        }
    }

    /**
     * Gets an specific anchor id details
     *
     * @param anchor to look for
     * @return a [Single] that will return a [Anchor] object
     * @throws [InvalidArgumentException] Informs that the input is not a number.
     * @throws [HttpRequestException] Error return by Enchainté's API.
     */
    fun getAnchor(anchor: Int): Single<Anchor> {
        return rxSingle {
            anchorService.getAnchor(anchor)
        }
    }

    /**
     * Waits until the anchor specified is confirmed in Enchainté
     *
     * @param anchor ID to wait for
     * @param timeout time in miliseconds. After exceeding this time returns an exception. Default = 120000
     * @return a [Single] that will return a [Anchor]
     * @throws [InvalidArgumentException] Informs that the input is not a number.
     * @throws [AnchorNotFoundException] The anchor provided could not be found.
     * @throws [WaitAnchorTimeoutException] Returned when the function has exceeded the timeout.
     * @throws [HttpRequestException] Error return by Enchainté's API.
     */
    @JvmOverloads
    fun waitAnchor(anchor: Int, timeout: Int = 120000): Single<Anchor> {
        return rxSingle {
            anchorService.waitAnchor(anchor, timeout)
        }
    }

    /**
     * Retrieves an integrity [Proof] for the specified list of [Message]
     *
     * @param messages to validate
     * @return a [Maybe] that will return a [Proof]
     * @throws [InvalidMessageException] At least one of the messages sent was not well formed.
     * @throws [HttpRequestException] Error returned by Enchainté's API.
     */
    fun getProof(messages: List<Message>): Maybe<Proof> {
        return rxMaybe {
            proofService.retrieveProof(messages)
        }
    }

    /**
     * Verifies if the specified integrity [Proof] is valid and checks if
     * it's currently included in the blockchain.
     *
     * @param proof to validate
     * @return a [Boolean] that returns true if valid, false if not
     * @throws [Web3Exception] Error connecting to blockchain.
     */
    fun verifyProof(proof: Proof): Int {
        return proofService.verifyProof(proof)
    }

    /**
     * It retrieves a proof for the specified list of [Anchor] using [getProof] and
     * verifies it using [verifyProof].
     *
     * @param messages to verify
     * @return a [Single] that will return true if valid, false if not.
     * @throws [InvalidMessageException] At least one of the messages sent was not well formed.
     * @throws [HttpRequestException] Error returned by Enchainté's API.
     * @throws [Web3Exception] Error connecting to blockchain.
     */
    fun verifyMessages(messages: List<Message>): Single<Int> {
        return rxSingle {
            proofService.verifyMessages(messages)
        }
    }

    companion object {
        const val VERSION: String = "1.1.0"
    }
}
