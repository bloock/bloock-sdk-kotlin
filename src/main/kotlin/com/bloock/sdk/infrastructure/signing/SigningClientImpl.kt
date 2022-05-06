package com.bloock.sdk.infrastructure.signing

import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.infrastructure.SigningClient
import com.bloock.sdk.infrastructure.signing.exception.UnsignedException
import com.bloock.sdk.shared.Signature
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.util.Base64
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import sun.security.ec.ECPublicKeyImpl


class SigningClientImpl : SigningClient {

    override fun sign(payload: ByteArray, rawPrivateKey: String, headers: Map<String, String>): Signature {
      TODO()
    }

    override fun verify(payload: ByteArray?, signatures: List<Signature>): Boolean {
        for (signature in signatures) {
            if (signature.header.kid != null) {
                try {
                    val signingKey = Base64(signature.header.kid).decode()
                    val josePublicKey = generateJWK(signingKey, null)

                    return josePublicKey.verify(ECDSAVerifier(ECPublicKeyImpl(signingKey)))

                } catch (e: JOSEException) {
                    throw UnsignedException()
                }
            }
        }
        return true
    }

    private fun generateJWK(signingKey: ByteArray, privateKey: ByteArray?): SignedJWT {
        val configData = ConfigData()

        val x = Base64.encode(signingKey.sliceArray(IntRange(1, 32)))
        val y = Base64.encode(signingKey.sliceArray(IntRange(33, 64)))
        val params: MutableMap<String, Any> = mutableMapOf()

        if (privateKey != null) {
            val d = Base64.encode(signingKey)
            params.put("crv", configData.configuration.ELLIPTIC_CURVE_KEY)
            params.put("kty", configData.configuration.KEY_TYPE_ALGORITHM)
            params.put("d", d)
            params.put("x", x)
            params.put("y", y)
        } else {
            params.put("crv", configData.configuration.ELLIPTIC_CURVE_KEY)
            params.put("kty", configData.configuration.KEY_TYPE_ALGORITHM)
            params.put("x", x)
            params.put("y", y)
        }


        val josePrivateKey = SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.ES256K).build(),
            JWTClaimsSet.parse(params)
        )

        return josePrivateKey
    }

}