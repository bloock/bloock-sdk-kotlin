import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.anchor.entity.exception.WaitAnchorTimeoutException
import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.infrastructure.http.exception.HttpRequestException
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
import com.enchainte.sdk.shared.entity.exception.InvalidArgumentException
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

    fun getSdk(): EnchainteClient {
        val apiKey = System.getenv("API_KEY")
        return EnchainteClient(apiKey, ConfigEnvironment.TEST)
    }

    @Test
    fun test_basic_e2e() {
        val client = getSdk()

        val messages = listOf(
            Message.fromString(randHex())
        )

        val receipts = client.sendMessages(messages).blockingGet()
        assertNotNull(receipts)

        client.waitAnchor(receipts[0].anchor).blockingGet()

        val proof = client.getProof(messages).blockingGet()
        val timestamp = client.verifyProof(proof)

        assertTrue(timestamp > 0)
    }

    @Test
    fun test_send_messages_invalid_message_input_wrong_char() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.sendMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_send_messages_invalid_message_input_missing_chars() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.sendMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_send_messages_invalid_message_input_wrong_start() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.sendMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_send_messages_empty_message_input() {
        val client = getSdk()

        client.sendMessages(emptyList())
            .test()
            .await()
            .assertResult(emptyList())
    }

    @Test
    fun test_get_messages_invalid_message_input_wrong_char() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.getMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_get_messages_invalid_message_input_missing_chars() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.getMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_get_messages_invalid_message_input_wrong_start() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.getMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_get_anchor_non_existing_anchor() {
        val client = getSdk()

        client.getAnchor(999999999)
            .test()
            .await()
            .assertFailure(HttpRequestException::class.java)

        // assertEquals(exception.message, "Anchor not found")
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
    fun test_get_proof_invalid_message_input_wrong_char() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.getProof(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_get_proof_invalid_message_input_missing_chars() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.getProof(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_get_proof_invalid_message_input_wrong_start() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.getProof(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_get_proof_empty_message_input() {
        val client = getSdk()

        client.getProof(emptyList())
            .test()
            .await()
            .assertFailure(InvalidArgumentException::class.java)
    }

    @Test
    fun test_get_proof_none_existing_leaf() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef")
        )

        client.getProof(messages)
            .test()
            .await()
            .assertFailure(HttpRequestException::class.java)

        // assertEquals(exception.message, "Message '0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef' not found.")
    }

    @Test
    fun test_verify_messages_invalid_message_input_wrong_char() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aG")
        )

        client.verifyMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_verify_messages_invalid_message_input_missing_chars() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994")
        )

        client.verifyMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_verify_messages_invalid_message_input_wrong_start() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994aa"),
            Message.fromHash("0xe016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994bb")
        )

        client.verifyMessages(messages)
            .test()
            .await()
            .assertFailure(InvalidMessageException::class.java)
    }

    @Test
    fun test_verify_messages_empty_message_input() {
        val client = getSdk()

        client.verifyMessages(emptyList())
            .test()
            .await()
            .assertFailure(InvalidArgumentException::class.java)
    }

    @Test
    fun test_verify_messages_none_existing_leaf() {
        val client = getSdk()

        val messages = listOf(
            Message.fromHash("0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef")
        )

        client.verifyMessages(messages)
            .test()
            .await()
            .assertFailure(HttpRequestException::class.java)

        // assertEquals(exception.message, "Message '0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef' not found.")
    }
}