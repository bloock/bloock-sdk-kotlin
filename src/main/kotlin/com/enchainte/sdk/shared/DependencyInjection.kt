package com.enchainte.sdk.shared

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
import io.ktor.client.features.auth.*
import io.ktor.client.features.json.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module

internal object EnchainteKoinContext {
    lateinit var koinApp : KoinApplication
}

internal fun setUpDependencyInjection() {
//    val koinApp = koinApplication {
//        modules(InfrastructureModule)
//        modules(ConfigModule)
//        modules(MessageModule)
//        modules(ProofModule)
//    }
//
//    EnchainteKoinContext.koinApp = koinApp
    startKoin {
        modules(InfrastructureModule)
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

internal val ConfigModule = module {
    single { ConfigServiceImpl(get()) as ConfigService }
    single { ConfigRepositoryImpl(get(), get()) as ConfigRepository }
    single { ConfigData() }
}

internal val MessageModule = module {
    single { MessageServiceImpl(get(), get()) as MessageService }
    single { MessageRepositoryImpl(get(), get()) as MessageRepository }
}

internal val ProofModule = module {
    single { ProofServiceImpl(get()) as ProofService }
    single { ProofRepositoryImpl(get(), get(), get()) as ProofRepository }
}
