package com.enchainte.sdk.proof

import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.BlockchainClient
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.post
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.proof.entity.Proof
import com.enchainte.sdk.proof.entity.dto.ProofRetrieveResponse
import com.enchainte.sdk.proof.repository.ProofRepository
import com.enchainte.sdk.proof.repository.ProofRepositoryImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProofRepositoryTest {

    @Test
    fun test_retrieve_proof_okay() {
        val blockchainClient = mockk<BlockchainClient>()
        val configService = mockk<ConfigService>()
        every { configService.getApiBaseUrl() } returns "api url"

        val httpClient = mockk<HttpClient>()
        coEvery { httpClient.post<ProofRetrieveResponse>(any(), any()) } returns ProofRetrieveResponse(
            bitmap = "bfdf7000",
            depth = "000400060006000500030002000400060007000800090009",
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            root = "c6372dab6a48637173a457e3ae0c54a500bb50346e847eccf2b818ade94d8ccf"
        )

        val proofRepository: ProofRepository = ProofRepositoryImpl(httpClient, blockchainClient, configService)

        runBlocking {
            val proof = proofRepository.retrieveProof(listOf(
                Message.fromHash("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            ))

            assertEquals(proof.bitmap, "bfdf7000")
            assertEquals(proof.depth, "000400060006000500030002000400060007000800090009")
            assertEquals(proof.leaves, listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"))
            assertEquals(proof.nodes, listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ))
        }
    }

    @Test
    fun test_verify_proof_keccak_1() {
        val proof = Proof(
            bitmap = "7600",
            depth = "0004000400030004000400030001",
            leaves = listOf(
                "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                "5e1712aca5f3925fc0ce628e7da2e1e407e2cc7b358e83a7152b1958f7982dab"
            ),
            nodes = listOf(
                "1ca0e9d9a206f08d38a4e2cf485351674ffc9b0f3175e0cb6dbd8e0e19829b97",
                "1ca0e9d9a206f08d38a4e2cf485351674ffc9b0f3175e0cb6dbd8e0e19829b97",
                "54944fcea707a57048c17ca7453fa5078a031143b44629776750e7f0ff7940f0",
                "d6f9bcd042be70b39b65dc2a8168858606b0a2fcf6d02c0a1812b1804efc0c37",
                "e663ec001b81b96eceabd1b766d49ec5d99adedc3e5f03d245b0d90f603f66d3"
            )
        )

        val blockchainClient = mockk<BlockchainClient>()
        val configService = mockk<ConfigService>()
        val httpClient = mockk<HttpClient>()

        val proofRepository: ProofRepository = ProofRepositoryImpl(httpClient, blockchainClient, configService)

        assertEquals(proofRepository.verifyProof(proof).getHash(), "a1fd8b878cee593a7debf12b5bcbf081a972bbec40e103c6d82197db2751ced7")
    }

    @Test
    fun test_verify_proof_keccak_2() {
        val proof = Proof(
            bitmap = "6d80",
            depth = "000500050004000400040004000400030001",
            leaves = listOf(
                "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                "5e1712aca5f3925fc0ce628e7da2e1e407e2cc7b358e83a7152b1958f7982dab"
            ),
            nodes = listOf(
                "1ca0e9d9a206f08d38a4e2cf485351674ffc9b0f3175e0cb6dbd8e0e19829b97",
                "1ca0e9d9a206f08d38a4e2cf485351674ffc9b0f3175e0cb6dbd8e0e19829b97",
                "1509877db1aa81c699a144d1a240c5d463c9ff08b2df489b40a35802844baeb6",
                "54944fcea707a57048c17ca7453fa5078a031143b44629776750e7f0ff7940f0",
                "d6f9bcd042be70b39b65dc2a8168858606b0a2fcf6d02c0a1812b1804efc0c37",
                "e663ec001b81b96eceabd1b766d49ec5d99adedc3e5f03d245b0d90f603f66d3"
            )
        )

        val blockchainClient = mockk<BlockchainClient>()
        val configService = mockk<ConfigService>()
        val httpClient = mockk<HttpClient>()

        val proofRepository: ProofRepository = ProofRepositoryImpl(httpClient, blockchainClient, configService)

        assertEquals(proofRepository.verifyProof(proof).getHash(), "7e1f3c7e6d3515389b6117cc8c1ef5512d51c59743dc097c70de405a91861d2b")
    }

    @Test
    fun test_verify_proof_keccak_3() {
        val proof = Proof(
            bitmap = "4000",
            depth = "00010001",
            leaves = listOf("0000000000000000000000000000000000000000000000000000000000000000"),
            nodes = listOf(
                "f49d70da1c2c8989766908e06b8d2277a6954ec8533696b9a404b631b0b7735a"
            )
        )

        val blockchainClient = mockk<BlockchainClient>()
        val configService = mockk<ConfigService>()
        val httpClient = mockk<HttpClient>()

        val proofRepository: ProofRepository = ProofRepositoryImpl(httpClient, blockchainClient, configService)

        assertEquals(proofRepository.verifyProof(proof).getHash(), "5c67902dc31624d9278c286ef4ce469451d8f1d04c1edb29a5941ca0e03ddc8d")
    }
}