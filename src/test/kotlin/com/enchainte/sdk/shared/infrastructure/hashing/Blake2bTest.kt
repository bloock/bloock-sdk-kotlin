package com.enchainte.sdk.shared.infrastructure.hashing

import com.enchainte.sdk.common.BaseTest
import com.enchainte.sdk.shared.Factory
import org.junit.Test
import kotlin.test.assertEquals

class Blake2bTest: BaseTest() {
    @Test
    fun testBlake2bHash() {
        val tests = mapOf(
            Pair("enchainte", "ab8e3ff984fce36be6e6cf01ec215df86556089bdebc20a663b4305f2fb67dc9"),
            Pair("hello world", "256c83b297114d201b30179f3f0ef0cace9783622da5974326b436178aeef610"),
            Pair("1234567890abcdef", "0a07f41abc26a390f836e77c2869f37be5c745fbceac4e15d2e2238c22f9db78")
        )

        val hashAlgorithm = Factory.getHashAlgorithm()
        for (test in tests) {
            assertEquals(
                test.value, hashAlgorithm.hash(test.key.toByteArray())
            )
        }
    }
}