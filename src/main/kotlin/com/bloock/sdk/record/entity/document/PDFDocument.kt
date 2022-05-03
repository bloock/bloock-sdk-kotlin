package com.bloock.sdk.record.entity.document

import com.google.gson.Gson
import org.apache.pdfbox.pdmodel.PDDocument
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type

class PDFDocument(src: ByteArray, args: DocumentLoadArgs? = null) : Document<ByteArray>(src, args) {
    private var source: PDDocument? = null

    override fun setup(src: ByteArray) {
        this.source = PDDocument.load(src)
        if (this.source!!.version != 1.7f) {
            this.source!!.version = 1.7f
            this.source!!.document.trailer.isNeedToBeUpdated = true
        }
    }

    override fun fetchMetadata(key: String): String? {
        if (this.source != null) {
            return this.source!!.documentInformation.getCustomMetadataValue(key)
        }

        return null
    }

    override fun fetchData(): ByteArray? {
        if (this.source != null) {
            val clone = this.source!!

            clone.documentInformation.setCustomMetadataValue(PROOF_KEY, null)
            clone.documentInformation.setCustomMetadataValue(SIGNATURES_KEY, null)

            return write(clone)
        }

        return null
    }

    override fun buildFile(metadata: Map<String, *>): ByteArray {
        if (this.source != null) {
            val clone = this.source!!
            val documentInformation = clone.documentInformation

            metadata.forEach { (key, value) ->
                run {
                    documentInformation.setCustomMetadataValue(key, Gson().toJson(value))
                }
            }

            return write(clone)
        }
        return ByteArray(0)
    }

    override fun getPayloadBytes(): ByteArray {
        if (this.getPayload() != null) {
            return this.getPayload()!!
        }
        return ByteArray(0)
    }

    override fun getDataBytes(): ByteArray {
        if (this.getData() != null) {
            return this.getData()!!
        }
        return ByteArray(0)
    }

    private fun write(document: PDDocument): ByteArray {
        val baos = ByteArrayOutputStream()
        document.save(baos)
        return baos.toByteArray()
    }
}
