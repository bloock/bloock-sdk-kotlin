package com.bloock.sdk.shared

import com.bloock.sdk.anchor.repository.AnchorRepository
import com.bloock.sdk.anchor.repository.AnchorRepositoryImpl
import com.bloock.sdk.anchor.service.AnchorService
import com.bloock.sdk.anchor.service.AnchorServiceImpl
import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.config.repository.ConfigRepository
import com.bloock.sdk.config.repository.ConfigRepositoryImpl
import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.config.service.ConfigServiceImpl
import com.bloock.sdk.infrastructure.BlockchainClient
import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.infrastructure.blockchain.Web3
import com.bloock.sdk.infrastructure.http.HttpClientData
import com.bloock.sdk.infrastructure.http.HttpClientImpl
import com.bloock.sdk.record.repository.RecordRepository
import com.bloock.sdk.record.repository.RecordRepositoryImpl
import com.bloock.sdk.record.service.RecordService
import com.bloock.sdk.record.service.RecordServiceImpl
import com.bloock.sdk.proof.repository.ProofRepository
import com.bloock.sdk.proof.repository.ProofRepositoryImpl
import com.bloock.sdk.proof.service.ProofService
import com.bloock.sdk.proof.service.ProofServiceImpl
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

    var recordRepository: RecordRepository = RecordRepositoryImpl(httpClient, configService)
    var recordService: RecordService = RecordServiceImpl(recordRepository)

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

    internal fun getRecordService(): RecordService {
        return recordService
    }

    internal fun getProofService(): ProofService {
        return proofService
    }

}
