import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
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

    fun getSdk(): EnchainteClient {
        val apiKey = System.getenv("API_KEY")
        return EnchainteClient(apiKey, ConfigEnvironment.TEST)
    }

    @Test
    fun testSendMessage() {
        val client = getSdk()

        val messages = listOf(
            Message.fromString(randHex())
        )

        val receipts = client.sendMessages(messages).blockingGet()
        assertNotNull(receipts)
        assertTrue(receipts[0].anchor > 0)
        assertTrue(receipts[0].client.isNotEmpty())
        assertEquals(receipts[0].message, messages[0].getHash())
        assertEquals(receipts[0].status, "Pending")
    }

    @Test
    fun testWaitAnchor() {
        val client = getSdk()

        val messages = listOf(
            Message.fromString(randHex()),
            Message.fromString(randHex()),
            Message.fromString(randHex())
        )

        val sendReceipt = client.sendMessages(messages).blockingGet()
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
    fun testFetchMessages() {
        val client = getSdk()

        val messages = listOf(
            Message.fromString(randHex()),
            Message.fromString(randHex()),
            Message.fromString(randHex())
        )

        val sendReceipt = client.sendMessages(messages).blockingGet()
        assertNotNull(sendReceipt)

        client.waitAnchor(sendReceipt[0].anchor).blockingSubscribe()

        val messageReceipts = client.getMessages(messages).blockingGet()
        for (messageReceipt in messageReceipts) {
            assertEquals(messageReceipt.status, "Success")
        }
    }

    @Test
    fun testGetProof() {
        val client = getSdk()

        val messages = listOf(
            Message.fromString(randHex()),
            Message.fromString(randHex()),
            Message.fromString(randHex())
        )

        val sendReceipt = client.sendMessages(messages).blockingGet()
        assertNotNull(sendReceipt)

        client.waitAnchor(sendReceipt[0].anchor).blockingSubscribe()

        val proof = client.getProof(messages).blockingGet()
        assertNotNull(proof)
    }

    @Test
    fun testVerifyProof() {
        val client = getSdk()

        val messages = listOf(
            Message.fromString(randHex()),
            Message.fromString(randHex()),
            Message.fromString(randHex())
        )

        val sendReceipt = client.sendMessages(messages).blockingGet()
        assertNotNull(sendReceipt)

        client.waitAnchor(sendReceipt[0].anchor).blockingSubscribe()

        val proof = client.getProof(messages).blockingGet()
        assertNotNull(proof)

        val timestamp = client.verifyProof(proof)
        assertTrue(timestamp > 0)
    }
}