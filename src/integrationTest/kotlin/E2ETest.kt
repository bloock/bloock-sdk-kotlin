import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertTrue

class E2ETest: AutoCloseKoinTest() {
    @Test
    fun e2eTest() {
        val charPool: List<Char> = ('a'..'f') + ('0'..'9')
        val randomHex = (1..16)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        val message = Message.fromHex(randomHex)

        val apiKey = System.getenv("API_KEY")
        val client = EnchainteClient(apiKey)
        println("SENDING MESSAGE: ${message.getHash()}")
        client.sendMessage(listOf(message)).blockingSubscribe()

        runBlocking {
            println("WAITING MESSAGE")
            client.waitMessageReceipts(listOf(message)).blockingSubscribe()

            println("VALIDATING MESSAGE")

            var valid = false
            val startTime = System.currentTimeMillis()
            val waitTime: Long = 120000
            val endTime = startTime + waitTime
            while (!valid && System.currentTimeMillis() < endTime) {
                valid = client.verifyMessages(listOf(message)).blockingGet()
                Thread.sleep(500)
            }

            assertTrue(valid)
        }
    }
}