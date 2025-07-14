package org.dmsextension.paperless.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestClient {

    @Test
    public void testBuilder() {
        try {
            new PaperlessClient.Builder().build();
            Assertions.fail();
        } catch (IllegalStateException ex) {
            Assertions.assertTrue(true);
        }

        try {
            PaperlessClient client = new PaperlessClient.Builder()
                    .host("ilostthegame")
                    .port(8000)
                    .user("sysuer")
                    .password("elo")
                    .protocol("http")
                    .build();
            Assertions.assertTrue(true);
        } catch (IllegalStateException ex) {
            Assertions.fail();
        }
    }

    @Test
    public void testFileUpload() {
        File file = new File("C:\\Users\\gerri\\Desktop\\King of Ordners\\2024_Ausbildungszeugnis.pdf");

        var client = new PaperlessClient.Builder()
                .url("http://ilostthegame:8000/api/")
                .user("sysuser")
                .password("elo")
                .build();

        Assertions.assertNotEquals("", client.uploadDocument(file));
    }
}
