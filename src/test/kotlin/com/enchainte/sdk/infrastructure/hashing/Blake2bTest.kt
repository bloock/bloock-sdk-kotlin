package com.enchainte.sdk.infrastructure.hashing

import com.enchainte.sdk.infrastructure.HashAlgorithm
import com.enchainte.sdk.shared.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Blake2bTest {
    @Test
    fun testBlake2bHash() {
        val tests = mapOf(
            Pair("Test Data", "7edb091de5d2cad1e65f9c124d3f3fda6895ec37b1bb0271aad78df6417a01e2"),
            Pair("Enchainte", "95b3e82e32782b9706afadbdb95ea6a690f603a6694db0935c8248185c28e92d"),
        )

        val hashAlgorithm: HashAlgorithm = Blake2b()
        for (test in tests) {
            assertEquals(
                test.value, hashAlgorithm.generateHash(Utils.stringToBytes(test.key))
            )
        }
    }
}