import com.enchainte.sdk.EnchainteClient;
import com.enchainte.sdk.message.entity.Message;
import com.enchainte.sdk.message.entity.MessageReceipt;
import org.junit.jupiter.api.Test;
import org.koin.test.junit5.AutoCloseKoinTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2EJavaTest extends AutoCloseKoinTest {
    @Test
    public void e2eJavaTest() {
        Message message = Message.fromHex(getRandomHexString());

        String apiKey = System.getenv("API_KEY");
        EnchainteClient client = new EnchainteClient(apiKey);

        System.out.println("SENDING MESSAGE: " + message.getHash());
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        List<MessageReceipt> writeResponse = client.sendMessage(messages).blockingGet();

        System.out.println("WAITING MESSAGE");
        client.waitAnchor(writeResponse.get(0).getAnchor()).blockingSubscribe();

        System.out.println("VALIDATING MESSAGE");

        boolean valid = client.verifyMessages(Collections.singletonList(message)).blockingGet();
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
