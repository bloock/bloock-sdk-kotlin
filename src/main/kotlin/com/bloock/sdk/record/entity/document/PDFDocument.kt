package com.bloock.sdk.record.entity.document

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.apache.pdfbox.cos.COSString
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDDocumentInformation
import java.io.ByteArrayOutputStream

const val METADATA_KEY = "_metadata_"
const val PROOF_KEY = "proof"
const val SIGNATURES_KEY = "signatures"

class PDFDocument(src: PdfDocumentContent, args: DocumentLoadArgs) : Document<PdfDocumentContent>(src, args) {
    private var source: PDDocument? = null

    override suspend fun fetchData(): Deferred<PdfDocumentContent?> {
        return GlobalScope.async {
            this@PDFDocument.source?.let {
                var clone = it

                var metadataMap = getMetadataMap(clone)

                metadataMap?.let {
                    removeMetadataValues(metadataMap, PROOF_KEY, clone)
                    removeMetadataValues(metadataMap, SIGNATURES_KEY, clone)
                }

                savePdf(clone)
            }
        }
    }

    private fun savePdf(document: PDDocument): PdfDocumentContent {
        var byteArrayOutputStream = ByteArrayOutputStream()
        document.save(byteArrayOutputStream)
        byteArrayOutputStream.close()
        return PdfDocumentContent(byteArrayOutputStream.toByteArray())
    }

    private fun removeMetadataValues(
        metadataMap: MutableMap<String, MutableMap<String, Any>>,
        key: String,
        clone: PDDocument
    ) {

        metadataMap.remove(key)
        val info = PDDocumentInformation()
        info.setCustomMetadataValue(METADATA_KEY, metadataMap.toString())
        clone.setDocumentInformation(info)
    }


    private fun getMetadataMap(clone: PDDocument): MutableMap<String, MutableMap<String, Any>>? {
        var dictionaryObject = clone
            .documentInformation
            .cosObject
            .getDictionaryObject(METADATA_KEY)
        return dictionaryObject?.let {
            var metadata = (dictionaryObject as COSString).string
            var metadataMap = Gson()
                .fromJson(metadata, MutableMap::class.java) as MutableMap<String, MutableMap<String, Any>>

            metadataMap

        } ?: null

    }

    override suspend fun setup(src: PdfDocumentContent): Deferred<Unit> {
        return GlobalScope.async {
            this@PDFDocument.source = PDDocument.load(src.content)
        }
    }

    override suspend fun fetchMetadata(key: String): Deferred<Any?> {
        return GlobalScope.async {
            this@PDFDocument.source?.let {
                return@async it.documentInformation.getCustomMetadataValue(key)
            }
        }
    }

    override suspend fun buildFile(metadata: Map<String, *>): Deferred<PdfDocumentContent> {
        return GlobalScope.async {
            this@PDFDocument.source?.let {
                val clone = it
                var documentInformation = clone.documentInformation
                documentInformation.setCustomMetadataValue(METADATA_KEY, Gson().toJson(metadata))

                savePdf(clone)
            }!!
        }
    }

    override fun getDocPayload() = Gson().toJson(this.payload?.content)

    override fun getDocData() = Gson().toJson(this.data?.content)

}


data class PdfDocumentContent(val content: ByteArray)
