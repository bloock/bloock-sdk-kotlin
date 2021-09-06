import com.bloock.sdk.BloockClient
import com.bloock.sdk.anchor.entity.exception.WaitAnchorTimeoutException
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.infrastructure.http.exception.HttpRequestException
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.exception.InvalidRecordException
import com.bloock.sdk.shared.entity.exception.InvalidArgumentException
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AcceptanceTest {

    fun randHex(): String {
        val charPool: List<Char> = ('a'..'f') + ('0'..'9')

        return (1..16)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun getSdk(): BloockClient {
        val apiKey = System.getenv("API_KEY")
        val apiHost = System.getenv("API_HOST")

        val client = BloockClient(apiKey)
        client.setApiHost(apiHost)
        return client
    }

    @Test
    fun test_basic_e2e() {
        val client = getSdk()

        val records = listOf(
            Record.fromString(randHex())
        )

        val receipts = client.sendRecords(records).blockingGet()
        assertNotNull(receipts)

        client.waitAnchor(receipts[0].anchor).blockingGet()

        val proof = client.getProof(records).blockingGet()
        val timestamp = client.verifyProof(proof, Network.BLOOCK_CHAIN)

        assertTrue(timestamp > 0)
    }

    @Test
    fun test_send_records_invalid_record_input_wrong_char() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.sendRecords(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_send_records_invalid_record_input_missing_chars() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.sendRecords(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_send_records_invalid_record_input_wrong_start() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.sendRecords(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_send_records_empty_record_input() {
        val client = getSdk()

        client.sendRecords(emptyList())
            .test()
            .await()
            .assertResult(emptyList())
    }

    @Test
    fun test_get_records_invalid_record_input_wrong_char() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.getRecords(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_get_records_invalid_record_input_missing_chars() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.getRecords(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_get_records_invalid_record_input_wrong_start() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.getRecords(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_get_anchor_non_existing_anchor() {
        val client = getSdk()

        client.getAnchor(999999999)
            .test()
            .await()
            .assertFailure(HttpRequestException::class.java)

        // assertEquals(exception.record, "Anchor not found")
    }

    @Test
    fun test_wait_anchor_non_existing_anchor() {
        val client = getSdk()

        client.waitAnchor(999999999, 3000)
            .test()
            .await()
            .assertFailure(WaitAnchorTimeoutException::class.java)
    }

    @Test
    fun test_get_proof_invalid_record_input_wrong_char() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.getProof(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_get_proof_invalid_record_input_missing_chars() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.getProof(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_get_proof_invalid_record_input_wrong_start() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.getProof(records)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_get_proof_empty_record_input() {
        val client = getSdk()

        client.getProof(emptyList())
            .test()
            .await()
            .assertFailure(InvalidArgumentException::class.java)
    }

    @Test
    fun test_get_proof_none_existing_leaf() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef")
        )

        client.getProof(records)
            .test()
            .await()
            .assertFailure(HttpRequestException::class.java)

        // assertEquals(exception.record, "Record '0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef' not found.")
    }

    @Test
    fun test_verify_records_invalid_record_input_wrong_char() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.verifyRecords(records, Network.BLOOCK_CHAIN)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_verify_records_invalid_record_input_missing_chars() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.verifyRecords(records, Network.BLOOCK_CHAIN)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_verify_records_invalid_record_input_wrong_start() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Record.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.verifyRecords(records, Network.BLOOCK_CHAIN)
            .test()
            .await()
            .assertFailure(InvalidRecordException::class.java)
    }

    @Test
    fun test_verify_records_empty_record_input() {
        val client = getSdk()

        client.verifyRecords(emptyList(), Network.BLOOCK_CHAIN)
            .test()
            .await()
            .assertFailure(InvalidArgumentException::class.java)
    }

    @Test
    fun test_verify_records_none_existing_leaf() {
        val client = getSdk()

        val records = listOf(
            Record.fromHash("0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef")
        )

        client.verifyRecords(records, Network.BLOOCK_CHAIN)
            .test()
            .await()
            .assertFailure(HttpRequestException::class.java)

        // assertEquals(exception.record, "Record '0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef' not found.")
    }
}