import com.bloock.sdk.BloockClient
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.record.entity.Record
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FunctionalTest {
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
    fun testSendRecord() {
        val client = getSdk()

        val records = listOf(
            Record.fromString(randHex())
        )

        val receipts = client.sendRecords(records).blockingGet()
        assertNotNull(receipts)
        assertTrue(receipts[0].anchor > 0)
        assertTrue(receipts[0].client.isNotEmpty())
        assertEquals(receipts[0].record, records[0].getHash())
        assertEquals(receipts[0].status, "Pending")
    }

    @Test
    fun testWaitAnchor() {
        val client = getSdk()

        val records = listOf(
            Record.fromString(randHex()),
            Record.fromString(randHex()),
            Record.fromString(randHex())
        )

        val sendReceipt = client.sendRecords(records).blockingGet()
        assertNotNull(sendReceipt)

        val receipt = client.waitAnchor(sendReceipt[0].anchor).blockingGet()
        assertNotNull(receipt)
        assertTrue(receipt.id > 0)
        assertTrue(receipt.blockRoots.isNotEmpty())
        assertTrue(receipt.networks.isNotEmpty())
        assertTrue(receipt.root.isNotEmpty())
        assertEquals(receipt.status, "Success")
    }

    @Test
    fun testFetchRecords() {
        val client = getSdk()

        val records = listOf(
            Record.fromString(randHex()),
            Record.fromString(randHex()),
            Record.fromString(randHex())
        )

        val sendReceipt = client.sendRecords(records).blockingGet()
        assertNotNull(sendReceipt)

        client.waitAnchor(sendReceipt[0].anchor).blockingSubscribe()

        val recordReceipts = client.getRecords(records).blockingGet()
        for (recordReceipt in recordReceipts) {
            assertEquals(recordReceipt.status, "Success")
        }
    }

    @Test
    fun testGetProof() {
        val client = getSdk()

        val records = listOf(
            Record.fromString(randHex()),
            Record.fromString(randHex()),
            Record.fromString(randHex())
        )

        val sendReceipt = client.sendRecords(records).blockingGet()
        assertNotNull(sendReceipt)

        client.waitAnchor(sendReceipt[0].anchor).blockingSubscribe()

        val proof = client.getProof(records).blockingGet()
        assertNotNull(proof)
    }

    @Test
    fun testVerifyProof() {
        val client = getSdk()

        val records = listOf(
            Record.fromString(randHex()),
            Record.fromString(randHex()),
            Record.fromString(randHex())
        )

        val sendReceipt = client.sendRecords(records).blockingGet()
        assertNotNull(sendReceipt)

        client.waitAnchor(sendReceipt[0].anchor).blockingSubscribe()

        val proof = client.getProof(records).blockingGet()
        assertNotNull(proof)

        //val timestamp = client.validateRoot(proof, Network.BLOOCK_CHAIN)
        //assertTrue(timestamp > 0)
    }
}