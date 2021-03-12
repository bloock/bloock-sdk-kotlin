import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class FunctionalTest: AutoCloseKoinTest() {
    @Test
    fun testSendMessage() {
        val apiKey = System.getenv("API_KEY")!!

        val client = EnchainteClient(apiKey)

        val message = Message.fromString("Example Data")
        val receipts = client.sendMessage(listOf(message)).blockingGet()
        assertNotNull(receipts)
    }

    @Test
    fun testWaitMessages() {
        val apiKey = System.getenv("API_KEY")!!

        val client = EnchainteClient(apiKey)

        val messages = listOf(
            Message.fromString("Example Data 1"),
            Message.fromString("Example Data 2"),
            Message.fromString("Example Data 3")
        )

        client.sendMessage(messages).blockingSubscribe()

        val receipts = client.waitMessageReceipts(messages).blockingGet()
        assertNotNull(receipts)
    }

    @Test
    fun testFetchMessages() {
        val apiKey = System.getenv("API_KEY")!!

        val client = EnchainteClient(apiKey)

        val messages = listOf(
            Message.fromString("Example Data 1"),
            Message.fromString("Example Data 2"),
            Message.fromString("Example Data 3")
        )

        client.sendMessage(messages).blockingSubscribe()

        client.waitMessageReceipts(messages).blockingSubscribe()

        val messageReceipts = client.getMessages(messages).blockingGet()
        for (messageReceipt in messageReceipts) {
            assertEquals("Success", messageReceipt.status)
        }
    }
}