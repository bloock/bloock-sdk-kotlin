import com.enchainte.sdk.EnchainteClient;
import com.enchainte.sdk.message.domain.Message;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2EJavaTest {
    @Test
    public void e2eJavaTest() throws Exception {
        Message message = Message.fromHex(getRandomHexString());

        String apiKey = System.getenv("API_KEY");
        EnchainteClient client = new EnchainteClient(apiKey);

        System.out.println("SENDING MESSAGE: " + message.getHash());
        client.sendMessage(message).blockingSubscribe();

        System.out.println("WAITING MESSAGE");
        client.waitMessageReceipts(Collections.singletonList(message)).blockingSubscribe();

        System.out.println("VALIDATING MESSAGE");

        boolean valid = false;
        long start_time = System.currentTimeMillis();
        long wait_time = 60000;
        long end_time = start_time + wait_time;
        while (!valid && System.currentTimeMillis() < end_time) {
            valid = client.verifyMessages(Collections.singletonList(message)).blockingGet();
            Thread.sleep(500);
        }

        assertTrue(valid);
    }

    private String getRandomHexString(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < 32){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.substring(0, 32);
    }
}
