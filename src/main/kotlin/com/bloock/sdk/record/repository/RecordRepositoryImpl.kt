package com.bloock.sdk.record.repository

import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.infrastructure.post
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.dto.*
import com.bloock.sdk.record.entity.dto.RecordRetrieveResponse
import com.bloock.sdk.record.entity.dto.RecordWriteRequest
import com.bloock.sdk.record.entity.dto.RecordWriteResponse

internal class RecordRepositoryImpl internal constructor(val httpClient: HttpClient, val configService: ConfigService) :
    RecordRepository {
    override suspend fun sendRecords(records: List<Record>): RecordWriteResponse {
        val url = "${this.configService.getApiBaseUrl()}/core/messages";
        return httpClient.post(url, RecordWriteRequest(messages = records.map { record -> record.getHash() }))
    }

    override suspend fun fetchRecords(records: List<Record>): List<RecordRetrieveResponse> {
        val url = "${this.configService.getApiBaseUrl()}/core/messages/fetch";
        return httpClient.post(url, RecordRetrieveRequest(messages = records.map { it.getHash() }))
    }
}