package com.enchainte.sdk.shared

import com.enchainte.sdk.anchor.repository.AnchorRepository
import com.enchainte.sdk.anchor.repository.AnchorRepositoryImpl
import com.enchainte.sdk.anchor.service.AnchorService
import com.enchainte.sdk.anchor.service.AnchorServiceImpl
import com.enchainte.sdk.config.data.ConfigData
import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.config.repository.ConfigRepositoryImpl
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.config.service.ConfigServiceImpl
import com.enchainte.sdk.infrastructure.BlockchainClient
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.blockchain.Web3
import com.enchainte.sdk.infrastructure.http.HttpClientData
import com.enchainte.sdk.infrastructure.http.HttpClientImpl
import com.enchainte.sdk.message.repository.MessageRepository
import com.enchainte.sdk.message.repository.MessageRepositoryImpl
import com.enchainte.sdk.message.service.MessageService
import com.enchainte.sdk.message.service.MessageServiceImpl
import com.enchainte.sdk.proof.repository.ProofRepository
import com.enchainte.sdk.proof.repository.ProofRepositoryImpl
import com.enchainte.sdk.proof.service.ProofService
import com.enchainte.sdk.proof.service.ProofServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*

internal object DependencyInjection {
    var http = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
    var httpClientData = HttpClientData()
    var httpClient: HttpClient = HttpClientImpl(http, httpClientData)

    var configData: ConfigData = ConfigData()
    var configRepository: ConfigRepository = ConfigRepositoryImpl(configData)
    var configService: ConfigService = ConfigServiceImpl(configRepository)

    var blockchainClient: BlockchainClient = Web3(configService)

    var anchorRepository: AnchorRepository = AnchorRepositoryImpl(httpClient, configService)
    var anchorService: AnchorService = AnchorServiceImpl(anchorRepository, configService)

    var messageRepository: MessageRepository = MessageRepositoryImpl(httpClient, configService)
    var messageService: MessageService = MessageServiceImpl(messageRepository)

    var proofRepository: ProofRepository = ProofRepositoryImpl(httpClient, blockchainClient, configService)
    var proofService: ProofService = ProofServiceImpl(proofRepository)

    internal fun getHttpClient(): HttpClient {
        return httpClient
    }

    internal fun getConfigService(): ConfigService {
        return configService
    }

    internal fun getAnchorService(): AnchorService {
        return anchorService
    }

    internal fun getMessageService(): MessageService {
        return messageService
    }

    internal fun getProofService(): ProofService {
        return proofService
    }

}
