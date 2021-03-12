package com.enchainte.sdk.infrastructure.hashing

import com.enchainte.sdk.infrastructure.HashAlgorithm
import com.enchainte.sdk.shared.Utils
import com.rfksystems.blake2b.Blake2b
import com.rfksystems.blake2b.security.Blake2bProvider
import java.security.MessageDigest
import java.security.Security

internal class Blake2b : HashAlgorithm {

    init {
        Security.addProvider(Blake2bProvider())
    }

    override fun generateHash(_data: ByteArray): String {
        val digest = MessageDigest.getInstance(Blake2b.BLAKE2_B_256)
        digest.update(_data)
        return Utils.bytesToHex(digest.digest())
    }
}