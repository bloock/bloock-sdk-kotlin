package com.bloock.sdk.record.entity.document

import com.bloock.sdk.shared.Signature
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.apache.pdfbox.cos.COSString
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDDocumentInformation
import java.io.BufferedOutputStream
import java.io.File

const val METADATA_KEY = "_metadata_"
const val PROOF_KEY = "proof"
const val SIGNATURES_KEY = "signatures"

class PDFDocument(src: PdfDocumentContent, args: DocumentLoadArgs) : Document<PdfDocumentContent>(src, args) {
    private lateinit var source: PDDocument

    override suspend fun fetchData(): Deferred<PdfDocumentContent?> {
        return GlobalScope.async {
            this@PDFDocument.source.let {
                var clone = it

                var metadataMap = getMetadataMap(clone)

                metadataMap?.let {
                    removeMetadataValues(metadataMap, PROOF_KEY, clone)
                    removeMetadataValues(metadataMap, SIGNATURES_KEY, clone)
                }

                return@async savePdfCopyOf(clone)
            }
        }
    }

    private fun savePdfCopyOf(document: PDDocument): PdfDocumentContent {

        var file = File.createTempFile("tmp", "pdf")
        document.save(BufferedOutputStream(file.outputStream()))
        try {
            return PdfDocumentContent(file.readBytes())
        } finally {
            file.delete()
        }

    }

    private fun removeMetadataValues(
        metadataMap: MutableMap<String, Any>,
        key: String,
        clone: PDDocument
    ) {

        metadataMap.remove(key)
        val info = PDDocumentInformation()
        info.setCustomMetadataValue(METADATA_KEY, metadataMap.toString())
        clone.setDocumentInformation(info)
    }


    private fun getMetadataMap(clone: PDDocument): MutableMap<String, Any>? {
        var dictionaryObject = clone
            .documentInformation
            .cosObject
            .getDictionaryObject(METADATA_KEY)
        return dictionaryObject?.let {
            var metadata = (dictionaryObject as COSString).string
            var metadataMap = Gson()
                .fromJson(metadata, MutableMap::class.java) as MutableMap<String, Any>

           return metadataMap

        } ?: null

    }

    override suspend fun setup(src: PdfDocumentContent): Deferred<Unit> {
        return GlobalScope.async {
            this@PDFDocument.source = PDDocument.load(src.content)
        }
    }

    override suspend fun fetchMetadata(key: String): Deferred<Any?> {
        return GlobalScope.async {
            this@PDFDocument.source.let { pdDocument ->

                getMetadataMap(pdDocument)?.let { metadata ->
                    if(metadata[key] is List<*>) {
                        return@async metadata[key] as MutableList<Signature>
                    }
                    return@async metadata[key]
                }
            }
        }
    }

    override suspend fun buildFile(metadata: Map<String, *>): Deferred<PdfDocumentContent> {
        return GlobalScope.async {
            this@PDFDocument.source?.let {
                val clone = it
                var documentInformation = clone.documentInformation
                documentInformation.setCustomMetadataValue(METADATA_KEY, Gson().toJson(metadata))

                return@async savePdfCopyOf(clone)
            }!!
        }
    }

    override fun getPayloadBytes() = this.payload?.content
    override fun getDataBytes() = this.data?.content
    override fun getDocPayload(): MutableMap<*,*>? {
        var customMetadataValue = PDDocument.load(getPayloadBytes()).documentInformation.getCustomMetadataValue(METADATA_KEY)
        return Gson().fromJson(customMetadataValue,Map::class.java) as MutableMap<*,*>?
    }
    override fun getDocData() = getDataBytes()
}


data class PdfDocumentContent(val content: ByteArray)
