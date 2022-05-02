package com.bloock.sdk.message.document

import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.record.entity.document.JsonDocument
import com.bloock.sdk.shared.Headers
import com.bloock.sdk.shared.Signature
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonDocumentTest {

    private val JSON_CONTENT: JsonElement = Gson().fromJson("""{"hello":"world"}""", JsonObject::class.java)

    @Test
    fun testConstructor() {
        val file = JsonDocument(JSON_CONTENT)

        val docPayload = file.getPayload()
        assertEquals(JSON_CONTENT, docPayload)
    }

    @Test
    fun testConstructorWithMetadata() {
        val json = mutableMapOf(
            Pair("_data_", JSON_CONTENT),
            Pair("_metadata_", mapOf<String, Any>(
                Pair("signatures", listOf(Signature("signature1", Headers())))
            ))
        )
        val file = JsonDocument(Gson().toJson(json))

        val docData = file.getData()
        assertEquals(JSON_CONTENT, docData)
    }

    @Test
    fun twoIdenticalFilesWithMetadataGeneratesSamePayload() {
        val file = JsonDocument(JSON_CONTENT)
        val file2 = JsonDocument(file.build())

        assertEquals(file.getData(), file2.getData())
        assertEquals(file.getPayload(), file2.getPayload())
        assertEquals(file.getProof(), file2.getProof())
        assertEquals(file.getSignatures(), file2.getSignatures())
    }

    @Test
    fun twoIdenticalFilesGeneratesSamePayload() {
        val json = mutableMapOf(
            Pair("_data_", JSON_CONTENT),
            Pair("_metadata_", mapOf<String, Any>(
                Pair("signatures", listOf(Signature("signature1", Headers())))
            ))
        )
        val file = JsonDocument(Gson().toJson(json))
        val file2 = JsonDocument(file.build())

        assertEquals(file.getData(), file2.getData())
        assertEquals(file.getPayload(), file2.getPayload())
        assertEquals(file.getProof(), file2.getProof())
        assertEquals(file.getSignatures(), file2.getSignatures())
    }

    @Test
    fun testSetProof() {
        val file = JsonDocument(JSON_CONTENT)
        val proof = Proof(listOf("leave1"), listOf("node1"), "depth", "bitmap")

        file.setProof(proof)
        assertEquals(file.getProof(), proof)

        val file2 = JsonDocument(file.build())
        assertEquals(file2.getProof(), proof)
    }

    @Test
    fun testSetSignature() {
        val file = JsonDocument(JSON_CONTENT)
        val signatures =
            mutableListOf(
                Signature("signature1", Headers()),
                Signature("signature2", Headers())
            )

        file.addSignature(signatures[0])
        file.addSignature(signatures[1])
        assertEquals(signatures, file.getSignatures())

        val file2 = JsonDocument(file.build())
        assertEquals(signatures, file2.getSignatures())
    }

    @Test
    fun testSetSignatureAndProof() {
        val file = JsonDocument(JSON_CONTENT)
        val signature = Signature("signature1", Headers())
        file.addSignature(signature)

        val proof = Proof(listOf("leave1"), listOf("node1"), "depth", "bitmap")
        file.setProof(proof)

        assertEquals(file.getSignatures(), mutableListOf(signature))
        assertEquals(file.getProof(), proof)

        val file2 = JsonDocument(file.build())
        assertEquals(file2.getSignatures(), mutableListOf(signature))
        assertEquals(file2.getProof(), proof)
    }

}
