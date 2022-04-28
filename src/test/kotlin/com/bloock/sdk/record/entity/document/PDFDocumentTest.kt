package com.bloock.sdk.record.entity.document

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class PDFDocumentTest {
    val bytes = File("src/test/kotlin/com/bloock/sdk/resources/dummy.pdf").readBytes()

    @Test
    fun testConstructor() {
        var file = PDFDocument(PdfDocumentContent(bytes), DocumentLoadArgs())
        runBlocking {

        }
    }
}
