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
import org.koin.core.context.startKoin
import org.koin.dsl.module

internal fun setUpDependencyInjection() {
    startKoin {
        modules(InfrastructureModule)
        modules(AnchorModule)
        modules(ConfigModule)
        modules(MessageModule)
        modules(ProofModule)
    }
}

internal val InfrastructureModule = module {
    single {
        HttpClient(CIO) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }
    single { HttpClientData() }
    single { HttpClientImpl(get(), get()) as HttpClient }
    single { Web3(get()) as BlockchainClient }
}

internal val AnchorModule = module {
    single { AnchorServiceImpl(get(), get()) as AnchorService }
    single { AnchorRepositoryImpl(get(), get()) as AnchorRepository }
}

internal val ConfigModule = module {
    single { ConfigServiceImpl(get()) as ConfigService }
    single { ConfigRepositoryImpl(get(), get()) as ConfigRepository }
    single { ConfigData() }
}

internal val MessageModule = module {
    single { MessageServiceImpl(get()) as MessageService }
    single { MessageRepositoryImpl(get(), get()) as MessageRepository }
}

internal val ProofModule = module {
    single { ProofServiceImpl(get()) as ProofService }
    single { ProofRepositoryImpl(get(), get(), get()) as ProofRepository }
}
