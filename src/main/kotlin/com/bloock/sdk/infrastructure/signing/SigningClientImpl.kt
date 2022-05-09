package com.bloock.sdk.infrastructure.signing

import com.bloock.sdk.config.data.ConfigData
import com.bloock.sdk.infrastructure.SigningClient
import com.bloock.sdk.infrastructure.signing.exception.InvalidPrivateKeyException
import com.bloock.sdk.infrastructure.signing.exception.UndefinedPrivateKeyException
import com.bloock.sdk.infrastructure.signing.exception.UnsignedException
import com.bloock.sdk.shared.Headers
import com.bloock.sdk.shared.Signature
import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jose.util.Base64URL
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.Sign
import sun.security.ec.ECPrivateKeyImpl
import java.math.BigInteger


class SigningClientImpl : SigningClient {

    override fun sign(payload: ByteArray, rawPrivateKey: String, headers: Map<String, String>): Signature {
        if (rawPrivateKey.isNullOrEmpty()) throw UndefinedPrivateKeyException()
        val configData = ConfigData()

        try {

            val privateKey = rawPrivateKey.encodeToByteArray()
            val publicKey = Sign.publicKeyFromPrivate(BigInteger(rawPrivateKey,16)).toByteArray()
            val josePrivateKey = generateJWK(publicKey = publicKey, privateKey = privateKey)


            val unprotectedHeader: MutableMap<String, Any> = mutableMapOf()
            unprotectedHeader.put("kty", configData.configuration.KEY_TYPE_ALGORITHM)
            unprotectedHeader.put("crv", configData.configuration.ELLIPTIC_CURVE_KEY)
            unprotectedHeader.put("alg", configData.configuration.SIGNATURE_ALGORITHM)
            unprotectedHeader.put("kid", Hex.encode(publicKey).toString())
            unprotectedHeader.putAll(headers)

            val jws = JWSObject(JWSHeader.parse(unprotectedHeader), Payload(payload))
            jws.sign(ECDSASigner(josePrivateKey!!.toECPrivateKey(), Curve.SECP256K1))

            val header = Headers(
                alg = jws.header.algorithm.name,
                crv = configData.configuration.ELLIPTIC_CURVE_KEY,
                kid = jws.header.keyID,
                kty = configData.configuration.KEY_TYPE_ALGORITHM,
                other = jws.header.customParams
            )
            return Signature(jws.signature.decodeToString(), header)

        } catch (e: Exception) {
            throw InvalidPrivateKeyException()
        }
    }

    override fun verify(payload: ByteArray?, signatures: List<Signature>): Boolean {
        for (signature in signatures) {
            if (signature.header.kid != null) {
                try {
                    val signingKey = signature.header.kid.encodeToByteArray()
                    val josePublicKey = generateJWK(signingKey, null)

                    val header = JWSHeader.Builder(JWSAlgorithm.ES256K)
                        .keyID(josePublicKey!!.getKeyID()).build()
                    val payloadObj = Payload(payload)
                    val jwsObject = JWSObject(header, payloadObj)

                    val verifier: JWSVerifier = ECDSAVerifier(josePublicKey)

                    return jwsObject.verify(verifier)

                } catch (e: JOSEException) {
                    throw UnsignedException()
                }
            }
        }
        return true
    }

    private fun generateJWK(publicKey: ByteArray, privateKey: ByteArray?): ECKey? {
        val configData = ConfigData()
        val x = Base64URL.encode(publicKey.sliceArray(IntRange(1, 32)))
        val y = Base64URL.encode(publicKey.sliceArray(IntRange(33, 64)))

        return if (privateKey != null) {
            val d = Base64URL.encode(privateKey)
            ECKey.Builder(Curve.forStdName(configData.configuration.ELLIPTIC_CURVE_KEY), x, y)
                .d(d).build()
        } else {
            ECKey.Builder(Curve.forStdName(configData.configuration.ELLIPTIC_CURVE_KEY), x, y)
                .privateKey(ECPrivateKeyImpl(privateKey))
                .build()
        }
    }

}