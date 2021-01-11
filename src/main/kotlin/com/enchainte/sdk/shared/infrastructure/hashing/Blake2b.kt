package com.enchainte.sdk.shared.infrastructure.hashing

import com.enchainte.sdk.shared.application.Utils
import com.enchainte.sdk.shared.application.HashAlgorithm
import com.rfksystems.blake2b.Blake2b
import com.rfksystems.blake2b.security.Blake2bProvider
import java.security.MessageDigest
import java.security.Security

internal class Blake2b: HashAlgorithm {
    init {
        Security.addProvider(Blake2bProvider())
    }

    override fun hash(message: ByteArray): String {
        val digest = MessageDigest.getInstance(Blake2b.BLAKE2_B_256)
        digest.update(message)
        return Utils.bytesToHex(digest.digest())
    }
}