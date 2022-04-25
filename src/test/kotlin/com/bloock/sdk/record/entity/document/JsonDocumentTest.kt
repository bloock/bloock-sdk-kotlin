package com.bloock.sdk.record.entity.document

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.Test

class JsonDocumentTest {

    private val JSON_CONTENT = """{"hello":"world"}""".trimMargin()

    @Test
    fun testConstructor() {
        val file = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())

        runBlocking {

            file.ready.await()
            val docPayload = file.getDocPayload()
            Assert.assertEquals(JSON_CONTENT, docPayload)
        }
    }

    @Test
    fun testConstructorWithMetadata() {
        val expectedContent = """{"hello":"world"}""".trimMargin()
        val signatures = """
            "signature":['signature1']
        """.trimIndent()
        var json = mutableMapOf<String,Any>(Pair("_data_", JSON_CONTENT), Pair("_metadata_", signatures))
        val file = JsonDocument(JsonDocumentContent(json), JsonDocumentLoadArgs())

        runBlocking {
            file.ready.await()
            val docData = file.getDocData()

            Assert.assertEquals(expectedContent, docData)
        }
    }

    @Test
    fun twoIdenticalFilesWithMetadataGeneratesSamePayload() {
        val signatures = """
            "signature":['signature1']
        """.trimIndent()
        var json = mutableMapOf<String,Any>(Pair("_data_", JSON_CONTENT), Pair("_metadata_", signatures))
        val file = JsonDocument(JsonDocumentContent(json), JsonDocumentLoadArgs())
        val file2 = JsonDocument(JsonDocumentContent(json), JsonDocumentLoadArgs())

        runBlocking {

            file.ready.await()
            val docPayload = file.getDocPayload()
            val docPayload2 = file2.getDocPayload()
            val docProof = file.getDocPayload()
            val docProof2 = file2.getDocPayload()
            val docSignatures = file.getDocSignatures()
            val docSignatures2 = file2.getDocSignatures()

            Assert.assertEquals(docPayload, docPayload2)
            Assert.assertEquals(docProof, docProof2)
            Assert.assertTrue(docSignatures.value!!.isNotEmpty())
            Assert.assertEquals(docSignatures, docSignatures2)
        }
    }

    @Test
    fun twoIdenticalFilesGeneratesSamePayload() {
        val file = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())
        val file2 = JsonDocument(JsonDocumentContent(mutableMapOf(Pair("hello", "world"))), JsonDocumentLoadArgs())

        runBlocking {

            file.ready.await()
            val docPayload = file.getDocPayload()
            val docPayload2 = file2.getDocPayload()
            val docProof = file.getDocPayload()
            val docProof2 = file2.getDocPayload()
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
        val signatures = Signatures(listOf("someSignature", "someSignature2"))

        file.addSignature(signatures)
        runBlocking {

            file.ready.await()
            val docSignatures = file.getDocSignatures()
            Assert.assertEquals(signatures, docSignatures)

            var src = file.build().await()
            val file2 = JsonDocument(src, JsonDocumentLoadArgs())
            file2.ready.await()

            val docSignatures2 = file2.getDocSignatures()
            Assert.assertEquals(docSignatures2, docSignatures)

        }
    }
}
