package com.enchainte.sdk.infrastructure.blockchain

import com.enchainte.sdk.infrastructure.blockchain.contract.CheckpointContract
import com.enchainte.sdk.shared.Utils
import org.junit.jupiter.api.Test
import org.web3j.EVMTest
import org.web3j.protocol.Web3j
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.ContractGasProvider
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@EVMTest
class Web3Test {
    @Test
    fun testGetCheckpoint(web3j: Web3j, transactionManager: TransactionManager, gasProvider: ContractGasProvider) {
        val contract = CheckpointContract.deploy(web3j, transactionManager, gasProvider).send()

        val checkpoint = "01dc3335809688de8a7206e47ed007c7bc56683cf5ab3c1761ee89f3e8c11a55"
        var result = contract.getCheckpoint(Utils.hexToBytes(checkpoint)).send()
        assertFalse(result)

        contract.addCheckpoint(Utils.hexToBytes(checkpoint)).send()

        result = contract.getCheckpoint(Utils.hexToBytes(checkpoint)).send()
        assertTrue(result)
    }
}