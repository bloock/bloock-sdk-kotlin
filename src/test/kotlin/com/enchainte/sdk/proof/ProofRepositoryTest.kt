package com.enchainte.sdk.proof

import com.enchainte.sdk.proof.entity.Proof
import com.enchainte.sdk.proof.repository.ProofRepository
import com.enchainte.sdk.shared.ConfigModule
import com.enchainte.sdk.shared.InfrastructureModule
import com.enchainte.sdk.shared.ProofModule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProofRepositoryTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            InfrastructureModule,
            ConfigModule,
            ProofModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun testVerifier1() {
        val leaves = listOf("72aae7e86eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9")
        val nodes = listOf(
            "359b5206452a4ca5058129727fb48f0860a36c0afee0ec62baa874927e9d4b99",
            "707cb86e449cd3990c85fb3ae9ec967ee12b82f21eae9e6ea35180e6c331c3e8",
            "23950edeb3ca719e814d8b04d63d90d39327b49b7df5baf2f72305c1f2b260b7",
            "72aae7e86eb50f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9",
            "517e320992fb35553575750153992d6360268d04a1e4d9e2cae7e5c3736ac627"
        )
        val depth = "020304050501"
        val bitmap = "f4"
        val root = "6608fd2c5d9c28124b41d6e441d552ad811a51fc6fdae0f33aa64bf3f43ca699"

        val proofRepository: ProofRepository = get()
        assertEquals(
            root,
            proofRepository.verifyProof(Proof(leaves, nodes, depth, bitmap))?.getHash()
        )
    }

    @Test
    fun testVerifier2() {
        val leaves = listOf(
            "82aae7e86eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9",
            "92aae7e86eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9"
        )
        val nodes = listOf(
            "285f570a90100fb94d5608b25d9e2b74bb58f068d495190f469aac5ef7ecf3c5",
            "8f0194b0986e0ea2d6e24df52f1fb3d44e421bce224383f7805f38dc772b3489"
        )
        val depth = "01030302"
        val bitmap = "a0"
        val root = "264248bf767509da977f61d42d5723511b7af2781613b9119edcebb25a226976"

        val proofRepository: ProofRepository = get()
        assertEquals(
            root,
            proofRepository.verifyProof(Proof(leaves, nodes, depth, bitmap))?.getHash()
        )
    }

    @Test
    fun testVerifier3() {
        val leaves = listOf(
            "82aae7e86eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9",
            "92aae7e86eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9"
        )
        val nodes = listOf(
            "285f570a90110fb94d5608b25d9e2b74bb58f068d495190f469aac5ef7ecf3c5",
            "8f0194b0986e0ea2d6e24df52f1fb3d44e421bce224383f7805f38dc772b3489"
        )
        val depth = "01030102"
        val bitmap = "f0"

        val proofRepository: ProofRepository = get()
        assertNull(proofRepository.verifyProof(Proof(leaves, nodes, depth, bitmap)))
    }

    @Test
    fun testVerifier4() {
        val leaves = listOf("72aae3286eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9")
        val nodes: List<String> = emptyList()
        val depth = "00"
        val bitmap = "00"
        val root = "72aae3286eb51f61a620831320475d9d61cbd52749dbf18fa942b1b97f50aee9"

        val proofRepository: ProofRepository = get()
        assertEquals(
            root,
            proofRepository.verifyProof(Proof(leaves, nodes, depth, bitmap))?.getHash()
        )
    }
}