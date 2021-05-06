package com.enchainte.sdk.infrastructure.hashing

import com.enchainte.sdk.infrastructure.HashAlgorithm
import com.enchainte.sdk.shared.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KeccakTest {
    @Test
    fun testKeccakHash() {
        val tests = mapOf(
            Pair("Test Data", "e287462a142cd6237de5a89891ad82065f6aca6644c161b1a61c933c5d26117a"),
            Pair("Enchainte", "d4144e508be94b010bd69d7a86837475b7c020b7abfb57ec164406f227e254f8"),
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