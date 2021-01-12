package com.enchainte.sdk.shared.infrastructure.blockchain

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.shared.application.BlockchainClient
import com.enchainte.sdk.shared.application.Utils
import com.enchainte.sdk.shared.infrastructure.blockchain.contract.CheckpointContract
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider

internal class Web3(private val config: ConfigService): BlockchainClient {
    override fun validateRoot(root: String): Boolean {
        val web3 = JsonRpc2_0Web3j(HttpService(config.getConfiguration().HTTP_PROVIDER))
        val contract = CheckpointContract.load(
            config.getConfiguration().CONTRACT_ADDRESS,
            web3,
            ReadonlyTransactionManager(web3, "0x0000000000000000000000000000000000000000"),
            DefaultGasProvider()
        )
        return contract.getCheckpoint(Utils.hexToBytes(root)).send() ?: false
    }
}