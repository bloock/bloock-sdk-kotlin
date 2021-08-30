import com.bloock.sdk.BloockClient;
import com.bloock.sdk.record.entity.Record;
import com.bloock.sdk.record.entity.RecordReceipt;
import com.bloock.sdk.proof.entity.Proof;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2EJavaTest {

    @Test
    public void BloockTest() {
        BloockClient client = getSdk();

        Record record = Record.fromHex(getRandomHexString());
        List<Record> records = new ArrayList<>();
        records.add(record);

        List<RecordReceipt> receipts = client.sendRecords(records).blockingGet();
        assertNotNull(receipts);

        client.waitAnchor(receipts.get(0).getAnchor()).blockingSubscribe();

        Proof proof = client.getProof(records).blockingGet();
        int timestamp = client.verifyProof(proof);

        assertTrue(timestamp > 0);
    }

    private BloockClient getSdk() {
        String apiKey = System.getenv("API_KEY");
        String apiHost = System.getenv("API_HOST");
        BloockClient client = new BloockClient(apiKey);
        client.setApiHost(apiHost);
        return client;
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
