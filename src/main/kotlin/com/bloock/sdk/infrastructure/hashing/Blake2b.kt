package com.bloock.sdk.infrastructure.hashing

import com.bloock.sdk.infrastructure.HashAlgorithm
import com.bloock.sdk.shared.Utils
import org.bouncycastle.jcajce.provider.digest.Blake2b
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

internal class Blake2b : HashAlgorithm {

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    override fun generateHash(_data: ByteArray): String {
        val digest = Blake2b.Blake2b256()
        digest.update(_data)
        return Utils.bytesToHex(digest.digest())
    }
}