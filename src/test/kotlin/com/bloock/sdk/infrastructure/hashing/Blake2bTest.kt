package com.bloock.sdk.infrastructure.hashing

import com.bloock.sdk.infrastructure.HashAlgorithm
import com.bloock.sdk.shared.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Blake2bTest {
    @Test
    fun testBlake2bHash() {
        val tests = mapOf(
            Pair("Test Data", "7edb091de5d2cad1e65f9c124d3f3fda6895ec37b1bb0271aad78df6417a01e2"),
            Pair("Bloock", "a19776f33b618530921959039204d5221d038c449bb1fe0cc1dfcfb7e4b00521"),
        )

        val hashAlgorithm: HashAlgorithm = Blake2b()
        for (test in tests) {
            assertEquals(
                test.value, hashAlgorithm.generateHash(Utils.stringToBytes(test.key))
            )
        }
    }
}