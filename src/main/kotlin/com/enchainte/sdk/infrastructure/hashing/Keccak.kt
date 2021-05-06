package com.enchainte.sdk.infrastructure.hashing

import com.enchainte.sdk.infrastructure.HashAlgorithm
import com.enchainte.sdk.shared.Utils
import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

internal class Keccak : HashAlgorithm {

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    override fun generateHash(_data: ByteArray): String {
        val digest = Keccak.Digest256()
        digest.update(_data)
        return Utils.bytesToHex(digest.digest())
    }
}