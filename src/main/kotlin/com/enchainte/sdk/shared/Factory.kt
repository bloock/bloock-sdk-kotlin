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
    internal var httpClient: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Auth) {
            /*bearer {
                apiKey = key
            }*/
        }
        /*install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }*/
    }
    private var config: ConfigService = ConfigService(httpClient)
    private var hashAlgorithm: HashAlgorithm = Blake2b()
    private var blockchainClient: BlockchainClient = Web3(config)

    // Message
    private var messageFindService: MessageFindService = MessageFindService(httpClient, config)
    private var messageWaitService: MessageWaitService = MessageWaitService(config)
    private var messageWriteService: MessageWriteService = MessageWriteService(httpClient, config)

    // Proof
    private var proofRetrieveService: ProofRetrieveService = ProofRetrieveService(httpClient, config)
    private var verifyService: ProofVerifyService = ProofVerifyService()

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