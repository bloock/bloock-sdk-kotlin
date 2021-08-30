package com.bloock.sdk.infrastructure.hashing

import com.bloock.sdk.infrastructure.HashAlgorithm
import com.bloock.sdk.shared.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KeccakTest {
    @Test
    fun testKeccakHash() {
        val tests = mapOf(
            Pair("Test Data", "e287462a142cd6237de5a89891ad82065f6aca6644c161b1a61c933c5d26117a"),
            Pair("Bloock", "3a7ae5d1ca472a7459e484babf13adf1aa7fe78326755969e3e2f5fc7766f6ee"),
            Pair("testing keccak", "7e5e383e8e70e55cdccfccf40dfc5d4bed935613dffc806b16b4675b555be139"),
        )

        val hashAlgorithm: HashAlgorithm = Keccak()
        for (test in tests) {
            assertEquals(
                test.value, hashAlgorithm.generateHash(Utils.stringToBytes(test.key))
            )
        }
    }
}