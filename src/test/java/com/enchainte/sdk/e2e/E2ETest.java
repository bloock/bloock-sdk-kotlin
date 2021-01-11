package com.enchainte.sdk.e2e;

import com.enchainte.sdk.EnchainteClient;
import com.enchainte.sdk.common.BaseTest;
import com.enchainte.sdk.message.domain.Message;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class E2ETest {
    @Test
    public void e2eJavaTest() throws Exception {
        Message message = Message.fromHex(getRandomHexString());

        EnchainteClient client = new EnchainteClient("xfJvoW4xZONxW2tDD5Vypwu3S23LfC-GaexO2epA7UjHilfFSm4gaMeZUuvcJtXz");
        System.out.println("SENDING MESSAGE: " + message.getHash());
        client.sendMessage(message).subscribe();

        System.out.println("WAITING MESSAGE");
        client.waitMessageReceipts(Collections.singletonList(message)).blockingSubscribe();

        System.out.println("VALIDATING MESSAGE");
        var valid = false;
        while (!valid) {
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
