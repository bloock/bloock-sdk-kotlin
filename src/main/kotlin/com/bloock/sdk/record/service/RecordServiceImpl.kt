package com.bloock.sdk.record.service

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.RecordReceipt
import com.bloock.sdk.record.entity.exception.InvalidRecordException
import com.bloock.sdk.record.repository.RecordRepository

internal class RecordServiceImpl internal constructor(
    private val recordRepository: RecordRepository
) : RecordService {

    override suspend fun sendRecords(records: List<Record>): List<RecordReceipt> {
        if (records.isEmpty()) {
            return emptyList()
        }

        if (records.any { !Record.isValid(it) }) {
            throw InvalidRecordException()
        }

        val response = recordRepository.sendRecords(records)

        val result: ArrayList<RecordReceipt> = ArrayList()
        for (record in records) {
            result.add(
                RecordReceipt(
                    response.anchor ?: 0,
                    response.client ?: "",
                    record.getHash(),
                    response.status ?: ""
                )
            )
        }

        return result
    }

    override suspend fun getRecords(records: List<Record>): List<RecordReceipt> {
        if (records.isEmpty()) {
            return emptyList()
        }

        if (records.any { !Record.isValid(it) }) {
            throw InvalidRecordException()
        }

        val response = recordRepository.fetchRecords(records)

        return response.map {
            val anchor: Int = it.anchor
            val record: String = it.message
            val client: String = it.client
            val status: String = it.status

            RecordReceipt(anchor, client, record, status)
        }
    }

}