package com.enchainte.sdk.shared

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.message.application.retrieve.MessageFindService
import com.enchainte.sdk.message.application.wait.MessageWaitService
import com.enchainte.sdk.message.application.write.MessageWriteService
import com.enchainte.sdk.proof.application.retrieve.ProofRetrieveService
import com.enchainte.sdk.proof.application.verify.ProofVerifyService
import com.enchainte.sdk.shared.application.BlockchainClient
import com.enchainte.sdk.shared.application.HashAlgorithm
import com.enchainte.sdk.shared.infrastructure.blockchain.Web3
import com.enchainte.sdk.shared.infrastructure.hashing.Blake2b
import com.enchainte.sdk.shared.infrastructure.http.bearer
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*

internal object Factory {

    // Infrastructure
    internal lateinit var httpClient: HttpClient
    private var hashAlgorithm: HashAlgorithm = Blake2b()
    private lateinit var blockchainClient: BlockchainClient

    // Config
    private lateinit var config: ConfigService

    // Message
    private lateinit var messageFindService: MessageFindService
    private lateinit var messageWaitService: MessageWaitService
    private lateinit var messageWriteService: MessageWriteService

    // Proof
    private lateinit var proofRetrieveService: ProofRetrieveService
    private lateinit var verifyService: ProofVerifyService

    fun load(key: String) {
        httpClient = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            install(Auth) {
                bearer {
                    apiKey = key
                }
            }
            /*install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }*/
        }
        config = ConfigService(httpClient)
        hashAlgorithm = Blake2b()
        blockchainClient = Web3(config)


        messageFindService = MessageFindService(httpClient, config)
        messageWaitService = MessageWaitService(config)
        messageWriteService = MessageWriteService(httpClient, config)

        proofRetrieveService = ProofRetrieveService(httpClient, config)
        verifyService = ProofVerifyService()
    }

    fun getHttpClient(): HttpClient {
        return httpClient
    }

    fun getHashAlgorithm(): HashAlgorithm {
        return hashAlgorithm
    }

    fun getConfig(): ConfigService {
        return config
    }

    fun getBlockchainClient(): BlockchainClient {
        return blockchainClient
    }

    fun getMessageFindService(): MessageFindService {
        return messageFindService
    }

    fun getMessageWaitService(): MessageWaitService {
        return messageWaitService
    }

    fun getMessageWriteService(): MessageWriteService {
        return messageWriteService
    }

    fun getProofService(): ProofRetrieveService {
        return proofRetrieveService
    }

    fun getVerifyService(): ProofVerifyService {
        return verifyService
    }
}