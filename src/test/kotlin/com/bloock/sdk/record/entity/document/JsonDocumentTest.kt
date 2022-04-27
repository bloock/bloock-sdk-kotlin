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

            file.ready
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
            file.ready
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

            file.ready
            val docPayload = file.getDocPayload()
            val docPayload2 = file2.getDocPayload()
            val docProof = file.getDocProf()
            val docProof2 = file2.getDocProf()
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

            file.ready
            val docPayload = file.getDocPayload()
            val docPayload2 = file2.getDocPayload()
            val docProof = file.getDocProf()
            val docProof2 = file2.getDocProf()
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
            file.ready
            file.addSignature(signatures.get(0))
            file.addSignature(signatures.get(1))
            val docSignatures = file.getDocSignatures()
            Assert.assertEquals(signatures, docSignatures)

            var src = file.build().await()
            val file2 = JsonDocument(src, JsonDocumentLoadArgs())
            file2.ready

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

            file.ready
            file.addSignature(expectedSignatures.get(0))
            file.addSignature(expectedSignatures.get(1))
            file.setDocProof(expectedProof)

            val docSignatures = file.getDocSignatures()
            val docProf = file.getDocProf()
            Assert.assertEquals(expectedSignatures, docSignatures)
            Assert.assertEquals(expectedProof, docProf)

            var src = file.build().await()
            val file2 = JsonDocument(src, JsonDocumentLoadArgs())
            file2.ready

            val docSignatures2 = file2.getDocSignatures()
            Assert.assertEquals(docSignatures2, docSignatures)

        }
    }

}
