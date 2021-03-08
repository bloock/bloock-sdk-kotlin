import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.domain.Message
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FunctionalTest {
    @Test
    fun testSendMessage() {
        val apiKey = System.getenv("API_KEY")!!

        val client = EnchainteClient(apiKey)

        val message = Message.fromString("Example Data")
        client.sendMessage(message).test().assertComplete()
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

        for (message in messages) {
            client.sendMessage(message).subscribe()
        }

        client.waitMessageReceipts(messages).test().assertComplete()
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

        for (message in messages) {
            client.sendMessage(message).subscribe()
        }

        client.waitMessageReceipts(messages).blockingSubscribe()

        val messageReceipts = client.getMessages(messages).blockingGet()
        for (messageReceipt in messageReceipts) {
            assertEquals("Success", messageReceipt.status)
        }
    }
}