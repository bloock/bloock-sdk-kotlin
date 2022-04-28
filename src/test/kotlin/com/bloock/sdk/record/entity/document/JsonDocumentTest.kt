package com.bloock.sdk.record.entity.document

import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.shared.Headers
import com.bloock.sdk.shared.Signature
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.Test

class JsonDocumentTest {

    private val JSON_CONTENT = """{"hello":"world"}""".trimMargin()

    @Test
    fun testConstructor() {
        val file = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())

        runBlocking {

            
            val docPayload = file.getDocPayload()
            Assert.assertEquals(JSON_CONTENT, docPayload)
        }
    }

    @Test
    fun testConstructorWithMetadata() {
        val signatures = mapOf<String, Any>(Pair("signatures", listOf("signature")))

        var json = mutableMapOf(Pair("_data_", JSON_CONTENT), Pair("_metadata_", signatures))
        val file = JsonDocument(JsonDocumentContent(json), JsonDocumentLoadArgs())

        runBlocking {
            
            val docData = file.getDocData()

            Assert.assertEquals(JSON_CONTENT, docData)
        }
    }

    @Test
    fun twoIdenticalFilesWithMetadataGeneratesSamePayload() {
        val signatures = mapOf<String, Any>(Pair("signatures", listOf("signature")))

        var json = mutableMapOf(Pair("_data_", JSON_CONTENT), Pair("_metadata_", signatures))
        val file = JsonDocument(JsonDocumentContent(json), JsonDocumentLoadArgs())
        val file2 = JsonDocument(JsonDocumentContent(json), JsonDocumentLoadArgs())

        runBlocking {

            
            val docPayload = file.getDocPayload()
            val docPayload2 = file2.getDocPayload()
            val docProof = file.getDocProof()
            val docProof2 = file2.getDocProof()
            val docSignatures = file.getDocSignatures()
            val docSignatures2 = file2.getDocSignatures()

            Assert.assertEquals(docPayload, docPayload2)
            Assert.assertEquals(docProof, docProof2)
            Assert.assertFalse(docSignatures.isNullOrEmpty())
            Assert.assertEquals(docSignatures, docSignatures2)
        }
    }

    @Test
    fun twoIdenticalFilesGeneratesSamePayload() {
        val file = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())
        val file2 = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())

        runBlocking {

            
            val docPayload = file.getDocPayload()
            val docPayload2 = file2.getDocPayload()
            val docProof = file.getDocProof()
            val docProof2 = file2.getDocProof()
            val docSignatures = file.getDocSignatures()
            val docSignatures2 = file2.getDocSignatures()

            Assert.assertEquals(docPayload, docPayload2)
            Assert.assertEquals(docProof, docProof2)
            Assert.assertEquals(docSignatures, docSignatures2)
        }
    }

    @Test
    fun testSetSignature() {
        val file = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())
        val signatures =
            mutableListOf(
                Signature("signature1", Headers()),
                Signature("signature2", Headers())
            )

        runBlocking {
            
            file.addSignature(signatures.get(0))
            file.addSignature(signatures.get(1))
            val docSignatures = file.getDocSignatures()
            Assert.assertEquals(signatures, docSignatures)

            var src = file.build().await()
            val file2 = JsonDocument(src, JsonDocumentLoadArgs())
            

            val docSignatures2 = file2.getDocSignatures()
            Assert.assertEquals(docSignatures2, docSignatures)

        }
    }

    @Test
    fun testSetSignatureAndProof() {

        val file = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())
        val expectedSignatures =
            mutableListOf(
                Signature("signature1", Headers()),
                Signature("signature2", Headers())
            )
        var expectedProof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            "deepth",
            "bitmap"
        )

        runBlocking {

            
            file.addSignature(expectedSignatures.get(0))
            file.addSignature(expectedSignatures.get(1))
            file.setDocProof(expectedProof)

            val docSignatures = file.getDocSignatures()
            val docProf = file.getDocProof()
            Assert.assertEquals(expectedSignatures, docSignatures)
            Assert.assertEquals(expectedProof, docProf)

            var src = file.build().await()
            val file2 = JsonDocument(src, JsonDocumentLoadArgs())
            

            val docSignatures2 = file2.getDocSignatures()
            Assert.assertEquals(docSignatures2, docSignatures)

        }
    }

}
