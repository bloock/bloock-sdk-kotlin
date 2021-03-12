import com.enchainte.sdk.EnchainteClient;
import com.enchainte.sdk.message.entity.Message;
import org.junit.jupiter.api.Test;
import org.koin.test.junit5.AutoCloseKoinTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2EJavaTest extends AutoCloseKoinTest {
    @Test
    public void e2eJavaTest() throws Exception {
        Message message = Message.fromHex(getRandomHexString());

        String apiKey = System.getenv("API_KEY");
        EnchainteClient client = new EnchainteClient(apiKey);

        System.out.println("SENDING MESSAGE: " + message.getHash());
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        client.sendMessage(messages).blockingSubscribe();

        System.out.println("WAITING MESSAGE");
        client.waitMessageReceipts(Collections.singletonList(message)).blockingSubscribe();

        System.out.println("VALIDATING MESSAGE");

        boolean valid = false;
        long start_time = System.currentTimeMillis();
        long wait_time = 90000;
        long end_time = start_time + wait_time;
        while (!valid && System.currentTimeMillis() < end_time) {
            valid = client.verifyMessages(Collections.singletonList(message)).blockingGet();
            Thread.sleep(500);
        }

        assertTrue(valid);
    }

    private String getRandomHexString() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 32) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.substring(0, 32);
    }
}
