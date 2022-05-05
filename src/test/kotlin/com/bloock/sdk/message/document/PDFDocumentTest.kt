package com.bloock.sdk.message.document

import com.bloock.sdk.anchor.entity.Anchor
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.record.entity.document.PDFDocument
import com.bloock.sdk.shared.Headers
import com.bloock.sdk.shared.Signature
import org.junit.jupiter.api.Test
import java.io.File
import java.util.Arrays
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PDFDocumentTest {
    private val bytes = File("src/test/kotlin/com/bloock/sdk/resources/dummy.pdf").readBytes()
    private val bytesWithMeta = File("src/test/kotlin/com/bloock/sdk/resources/dummy-with-metadata.pdf").readBytes()

    @Test
    fun testConstructor() {
        val file = PDFDocument(bytes)

        val docPayload = file.getPayload()
        assertTrue(docPayload!!.isNotEmpty())
    }

    @Test
    fun `test constructor with metadata`() {
        val file = PDFDocument(bytesWithMeta)
        val docPayload = file.getPayload()
        assertTrue(docPayload!!.isNotEmpty())
    }

    @Test
    fun test_two_same_files_generates_same_payload() {
        val file = PDFDocument(bytes)
        val file2 = PDFDocument(file.build())

        assertTrue(Arrays.equals(file.getData(), file2.getData()))
        assertTrue(Arrays.equals(file.getPayload(), file2.getPayload()))
        assertEquals(file.getProof(), file2.getProof())
        assertEquals(file.getSignatures(), file2.getSignatures())
    }

    @Test
    fun test_two_same_files_with_metadata_generates_same_payload() {
        val file = PDFDocument(bytesWithMeta)
        val file2 = PDFDocument(file.build())


        assertTrue(Arrays.equals(file.getData(), file2.getData()))
        assertTrue(Arrays.equals(file.getPayload(), file2.getPayload()))
        assertEquals(file.getProof(), file2.getProof())
        assertEquals(file.getSignatures(), file2.getSignatures())
    }

    @Test
    fun `test set proof`() {
        val file = PDFDocument(bytes)
        val proof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            depth = "depth",
            bitmap = "bitmap",
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            ),
            networks = emptyList()
        )


        file.setProof(proof)
        assertEquals(file.getProof(), proof)

        val file2 = PDFDocument(file.build())
        assertEquals(file2.getProof(), proof)
    }

    @Test
    fun `test set signature`() {
        val file = PDFDocument(bytes)
        val signatures =
        mutableListOf(
            Signature("signature1", Headers()),
            Signature("signature2", Headers())
        )

        file.addSignature(signatures[0])
        file.addSignature(signatures[1])
        assertEquals(signatures, file.getSignatures())

        val file2 = PDFDocument(file.build())
        assertEquals(signatures, file2.getSignatures())
    }

    @Test
    fun `test set signature and proof`() {
        val file = PDFDocument(bytes)
        val signature = Signature("signature1", Headers())
        file.addSignature(signature)

        val proof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            depth = "depth",
            bitmap = "bitmap",
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            ),
            networks = emptyList()
        )

        file.setProof(proof)

        assertEquals(file.getSignatures(), mutableListOf(signature))
        assertEquals(file.getProof(), proof)

        val file2 = PDFDocument(file.build())
        assertEquals(file2.getSignatures(), mutableListOf(signature))
        assertEquals(file2.getProof(), proof)
    }
}
