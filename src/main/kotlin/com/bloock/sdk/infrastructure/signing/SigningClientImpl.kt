package com.bloock.sdk.infrastructure.signing

import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.infrastructure.SigningClient
import com.bloock.sdk.infrastructure.signing.exception.UnsignedException
import com.bloock.sdk.shared.Signature
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.util.Base64
import com.nimbusds.jose.util.Base64URL
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.Sign
import sun.security.ec.ECPrivateKeyImpl
import sun.security.ec.ECPublicKeyImpl
import java.math.BigInteger


class SigningClientImpl : SigningClient {

    override fun sign(payload: ByteArray, rawPrivateKey: String, headers: Map<String, String>): Signature {
        val configData = ConfigData()

        val privateKey = Hex.encode(rawPrivateKey.toByteArray())
        val publicKey = Sign.publicKeyFromPrivate(BigInteger(rawPrivateKey)).toByteArray()
        val josePrivateKey = generateJWK(publicKey = publicKey,privateKey = privateKey)

        josePrivateKey.sign(ECDSASigner(ECPrivateKeyImpl(privateKey), Curve.SECP256K1))

        val unprotectedHeader: MutableMap<String, Any> = mutableMapOf()
        unprotectedHeader.put("kty", configData.configuration.KEY_TYPE_ALGORITHM)
        unprotectedHeader.put("crv", configData.configuration.ELLIPTIC_CURVE_KEY)
        unprotectedHeader.put("alg", configData.configuration.SIGNATURE_ALGORITHM)
        unprotectedHeader.put("kid", Hex.encode(publicKey))
        unprotectedHeader.putAll(headers)


    }

    override fun verify(payload: ByteArray?, signatures: List<Signature>): Boolean {
        for (signature in signatures) {
            if (signature.header.kid != null) {
                try {
                    val signingKey = Hex.decode(signature.header.kid)
                    val josePublicKey = generateJWK(signingKey, null)

                    return josePublicKey.verify(ECDSAVerifier(ECPublicKeyImpl(signingKey)))

                } catch (e: JOSEException) {
                    throw UnsignedException()
                }
            }
        }
        return true
    }

    private fun generateJWK(publicKey: ByteArray, privateKey: ByteArray?): SignedJWT {
        val configData = ConfigData()

        val x = Base64URL.encode(publicKey.sliceArray(IntRange(1, 32)))
        val y = Base64URL.encode(publicKey.sliceArray(IntRange(33, 64)))
        val params: MutableMap<String, Any> = mutableMapOf()

        if (privateKey != null) {
            val d = Base64.encode(publicKey)
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