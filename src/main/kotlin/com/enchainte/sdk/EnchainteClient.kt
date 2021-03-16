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
import com.enchainte.sdk.shared.setUpDependencyInjection
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.rxMaybe
import kotlinx.coroutines.rx3.rxSingle
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

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
class EnchainteClient(private val apiKey: String, private val environment: ConfigEnvironment) : KoinComponent {

    constructor(apiKey: String) : this(apiKey, ConfigEnvironment.TEST)

    private var anchorService: AnchorService
    private var configService: ConfigService
    private var messageService: MessageService
    private var proofService: ProofService

    private var httpClient: HttpClient

    init {
        setUpDependencyInjection()

        anchorService = get()
        configService = get()
        messageService = get()
        proofService = get()
        httpClient = get()

        httpClient.setApiKey(apiKey)

        runBlocking {
            configService.setupEnvironment(environment)
        }
    }

    /**
     * Sends a list of [Anchor] to Enchainté
     *
     * @param messages list of [Anchor] to send
     * @return RxJava [Single] that will return a list of [MessageReceipt]
     */
    fun sendMessage(messages: List<Message>): Single<List<MessageReceipt>> {
        return rxSingle {
            messageService.sendMessages(messages)
        }
    }

    /**
     * Retrieves the [MessageReceipt]s for the specified [Anchor]s
     *
     * @param messages to fetch
     * @return a [Single] that will return a list of [MessageReceipt]
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
     */
    fun getAnchor(anchor: Int): Single<Anchor> {
        return rxSingle {
            anchorService.getAnchor(anchor)
        }
    }

    /**
     * Waits until the anchor specified is confirmed in Enchainté
     *
     * @param anchor to wait for
     * @return a [Single] that will return a [Anchor]
     */
    fun waitAnchor(anchor: Int): Single<Anchor> {
        return rxSingle {
            anchorService.waitAnchor(anchor)
        }
    }

    /**
     * Retrieves an integrity [Proof] for the specified list of [Anchor]
     *
     * @param messages to validate
     * @return a [Maybe] that will return a [Proof]
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
     */
    fun verifyProof(proof: Proof): Boolean {
        return proofService.verifyProof(proof)
    }

    /**
     * It retrieves a proof for the specified list of [Anchor] using [getProof] and
     * verifies it using [verifyProof].
     *
     * @param messages to verify
     * @return a [Single] that will return true if valid, false if not.
     */
    fun verifyMessages(messages: List<Message>): Single<Boolean> {
        return rxSingle {
            proofService.verifyMessages(messages)
        }
    }

    companion object {
        const val VERSION: String = "0.2.0"
    }
}
