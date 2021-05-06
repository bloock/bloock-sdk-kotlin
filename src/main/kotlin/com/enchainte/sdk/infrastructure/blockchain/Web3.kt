package com.enchainte.sdk.infrastructure.blockchain

import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.BlockchainClient
import com.enchainte.sdk.infrastructure.blockchain.contract.EnchainteState
import com.enchainte.sdk.shared.Utils
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider

internal class Web3(private val config: ConfigService) : BlockchainClient {
    override fun validateRoot(root: String): Int {
        val web3 = JsonRpc2_0Web3j(HttpService(config.getConfiguration().HTTP_PROVIDER))
        val contract = EnchainteState.load(
            config.getConfiguration().CONTRACT_ADDRESS,
            web3,
            ReadonlyTransactionManager(web3, "0x0000000000000000000000000000000000000000"),
            DefaultGasProvider()
        )

        return contract.getState(Utils.hexToBytes(root)).send().intValueExact()
    }
}