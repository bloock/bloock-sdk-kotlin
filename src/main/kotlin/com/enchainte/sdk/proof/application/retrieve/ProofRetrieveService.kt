package com.enchainte.sdk.proof.application.retrieve

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.message.domain.Message
import com.enchainte.sdk.proof.application.retrieve.dto.RetrieveProofRequest
import com.enchainte.sdk.proof.application.retrieve.dto.RetrieveProofResponse
import com.enchainte.sdk.proof.domain.Proof
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class ProofRetrieveService(private val httpClient: HttpClient, private val config: ConfigService) {

    suspend fun getProof(messages: List<Message>): Proof? {
        return try {
            val url = "${config.getConfiguration().HOST}${config.getConfiguration().PROOF_ENDPOINT}"
            val requestBody = RetrieveProofRequest(messages.map { it.getHash() })
            val response = httpClient.post<RetrieveProofResponse> {
                url(url)
                contentType(ContentType.Application.Json)
                body = requestBody
            }

            Proof(requestBody.hashes, response.nodes, response.depth, response.bitmap)
        } catch (t: Throwable) {
            println("Error: ${t.message}")
            null
        }
    }
}