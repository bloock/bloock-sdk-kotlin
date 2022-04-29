package com.bloock.sdk.record.entity.document

import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.shared.Headers
import com.bloock.sdk.shared.Signature
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.apache.pdfbox.pdmodel.PDDocument
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class PDFDocumentTest {
    val bytes = File("src/test/kotlin/com/bloock/sdk/resources/dummy.pdf").readBytes()
    val bytesWithMeta = File("src/test/kotlin/com/bloock/sdk/resources/dummy-with-metadata.pdf").readBytes()

    @Test
    fun testConstructor() {
        runBlocking {
            var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())

            var docPayload = file.getDocPayload()
            assertTrue(docPayload.isNullOrEmpty())
        }
    }

    @Test
    fun `test constructor with metadata`() {

        var someMeta = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            "deepth",
            "bitmap"
        )
        runBlocking {
            var file = PDFDocument(PdfDocumentContent(bytesWithMeta), DocumentLoadArgs())
            file.setDocProof(someMeta)
            var copy = file.build().await()

            var metadataValues = PDDocument.load(copy.content).documentInformation.getCustomMetadataValue("_metadata_")
            var payload = Gson().fromJson(metadataValues, Map::class.java) as MutableMap<*, *>?


            assertTrue(payload!!.isNotEmpty())
            assertTrue(payload.containsKey("proof"))

        }
    }
    private fun metadataKeyExist(pdf: ByteArray, key: String): Boolean {
        var doc = PDDocument.load(pdf)
        var dictionaryObject = doc.documentInformation.cosObject.getDictionaryObject(key)

        return dictionaryObject != null
    }
    @Test
    fun test_two_same_files_generates_same_payload() {
        runBlocking {
            var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
            var file2 = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())

            var docPayload = file.getDocPayload() as MutableMap<*, *>
            assertTrue(docPayload.isEmpty())
            assertEquals(docPayload, file2.getDocPayload())
        }
    }

    @Test
    fun test_two_same_files_with_metadata_generates_same_payload() {
        var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
        var file2 = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
        var proof = Proof(
            leaves = arrayListOf("leave1"),
            nodes = arrayListOf("node1"),
            "deepth",
            "bitmap"
        )
        runBlocking {
            file.setDocProof(proof)
            file2.setDocProof(proof)

            var copy = file.build().await()
            var copy2 = file2.build().await()


            var payload = Gson().fromJson(PDDocument.load(copy.content).documentInformation.getCustomMetadataValue("_metadata_"), Map::class.java) as MutableMap<*, *>?
            var payload2 = Gson().fromJson(PDDocument.load(copy2.content).documentInformation.getCustomMetadataValue("_metadata_"), Map::class.java) as MutableMap<*, *>?

            assertTrue(payload!!.isNotEmpty())
            assertTrue(payload2!!.isNotEmpty())
            assertTrue(payload.containsKey("proof"))
            assertEquals(proof, Gson().fromJson(payload["proof"].toString(),Proof::class.java))
            assertEquals(payload,payload2)
        }
    }

    @Test
    fun `test set proof`() {

        var expectedProof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            "deepth",
            "bitmap"
        )
        var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
        runBlocking {
            file.setDocProof(expectedProof)
            assertEquals(expectedProof, file.getDocProof())

        }
    }

    @Test
    fun `test set signature`() {
        val signatures =
            mutableListOf(
                Signature("signature1", Headers()),
                Signature("signature2", Headers())
            )

        var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
        runBlocking {
            file.addSignature(signatures[0])
            file.addSignature(signatures[1])

            assertEquals(signatures, file.getDocSignatures())

            var content = file.build().await()
            var file2 = PDFDocument(
                content,
                DocumentLoadArgs()
            )

            assertEquals(signatures, file2.getDocSignatures())
        }
    }

    @Test
    fun `test set signature and proof`() {
        val signatures =
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
        var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
        runBlocking {
            file.addSignature(signatures[0])
            file.addSignature(signatures[1])
            file.setDocProof(expectedProof)

            var docSignatures = file.getDocSignatures()
            assertEquals(signatures, docSignatures)
            assertEquals(expectedProof, file.getDocProof())

            var content = file.build().await()
            var file2 = PDFDocument(
                content,
                DocumentLoadArgs()
            )

            var docSignatures1 = file2.getDocSignatures()
            assertEquals(signatures, docSignatures1)
            assertEquals(expectedProof, file2.getDocProof())
        }
    }
}
