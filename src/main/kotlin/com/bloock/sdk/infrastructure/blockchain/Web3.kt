package com.bloock.sdk.infrastructure.blockchain

import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.infrastructure.BlockchainClient
import com.bloock.sdk.infrastructure.blockchain.contract.BloockState
import com.bloock.sdk.shared.Utils
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider

internal class Web3(private val config: ConfigService) : BlockchainClient {
    override fun validateRoot(root: String, network: Network): Int {
        val networkConfig = config.getNetworkConfiguration(network)

        val web3 = JsonRpc2_0Web3j(HttpService(networkConfig.HTTP_PROVIDER))
        val contract = BloockState.load(
            networkConfig.CONTRACT_ADDRESS,
            web3,
            ReadonlyTransactionManager(web3, "0x0000000000000000000000000000000000000000"),
            DefaultGasProvider()
        )

        var timestamp = contract.getState(Utils.hexToBytes(root)).send().intValueExact()
        return timestamp
    }
}