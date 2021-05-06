import com.enchainte.sdk.EnchainteClient;
import com.enchainte.sdk.config.entity.ConfigEnvironment;
import com.enchainte.sdk.message.entity.Message;
import com.enchainte.sdk.message.entity.MessageReceipt;
import com.enchainte.sdk.proof.entity.Proof;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2EJavaTest {

    @Test
    public void e2eJavaTest() {
        EnchainteClient client = getSdk();

        Message message = Message.fromHex(getRandomHexString());
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        List<MessageReceipt> receipts = client.sendMessages(messages).blockingGet();
        assertNotNull(receipts);

        client.waitAnchor(receipts.get(0).getAnchor()).blockingSubscribe();

        Proof proof = client.getProof(messages).blockingGet();
        int timestamp = client.verifyProof(proof);

        assertTrue(timestamp > 0);
    }

    private EnchainteClient getSdk() {
        String apiKey = System.getenv("API_KEY");
        return new EnchainteClient(apiKey, ConfigEnvironment.TEST);
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
