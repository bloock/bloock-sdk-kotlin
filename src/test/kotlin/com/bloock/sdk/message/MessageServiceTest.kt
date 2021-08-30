package com.bloock.sdk.record

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.dto.RecordRetrieveResponse
import com.bloock.sdk.record.entity.dto.RecordWriteResponse
import com.bloock.sdk.record.entity.exception.InvalidRecordException
import com.bloock.sdk.record.repository.RecordRepository
import com.bloock.sdk.record.service.RecordService
import com.bloock.sdk.record.service.RecordServiceImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RecordServiceTest {
    @Test
    fun test_send_records_okay() {
        val recordRepository = mockk<RecordRepository>()
        coEvery { recordRepository.sendRecords(any()) } returns RecordWriteResponse(
            anchor = 80,
            client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
            messages = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            status = "Pending"
        )

        val recordService: RecordService = RecordServiceImpl(recordRepository)

        runBlocking {
            val result = recordService.sendRecords(listOf(
                Record.fromHash("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            ))

            assertEquals(result[0].anchor, 80)
            assertEquals(result[0].client, "ce10c769-022b-405e-8e7c-3b52eeb2a4ea")
            assertEquals(result[0].record, "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            assertEquals(result[0].status, "Pending")
        }
    }

    @Test
    fun test_send_records_some_invalid_record_error() {
        val recordRepository = mockk<RecordRepository>()
        coEvery { recordRepository.sendRecords(any()) } returns RecordWriteResponse(
            anchor = 80,
            client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
            messages = listOf(
                "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                "record2",
                ""
            ),
            status = "Pending"
        )

        val recordService: RecordService = RecordServiceImpl(recordRepository)

        runBlocking {
            assertFailsWith<InvalidRecordException> {
                recordService.sendRecords(listOf(
                    Record.fromHash("record")
                ))
            }
        }
    }

    @Test
    fun test_get_records_okay() {
        val recordRepository = mockk<RecordRepository>()
        coEvery { recordRepository.fetchRecords(any()) } returns listOf(
            RecordRetrieveResponse(
                anchor = 80,
                client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
                message = "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                status = "Pending"
            )
        )

        val recordService: RecordService = RecordServiceImpl(recordRepository)

        runBlocking {
            val result = recordService.getRecords(listOf(
                Record.fromString("record")
            ))

            assertEquals(result[0].anchor, 80)
            assertEquals(result[0].client, "ce10c769-022b-405e-8e7c-3b52eeb2a4ea")
            assertEquals(result[0].record, "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            assertEquals(result[0].status, "Pending")
        }
    }
}