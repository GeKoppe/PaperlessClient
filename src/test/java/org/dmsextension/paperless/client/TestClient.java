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
                    .host(System.getenv("PAPERLESS_HOST"))
                    .port(System.getenv("PAPERLESS_PORT"))
                    .user(System.getenv("PAPERLESS_USER"))
                    .password(System.getenv("PAPERLESS_PW"))
                    .protocol(System.getenv("PAPERLESS_PROTOCOL"))
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
                .host(System.getenv("PAPERLESS_HOST"))
                .port(System.getenv("PAPERLESS_PORT"))
                .user(System.getenv("PAPERLESS_USER"))
                .password(System.getenv("PAPERLESS_PW"))
                .protocol(System.getenv("PAPERLESS_PROTOCOL"))
                .build();

        Assertions.assertNotEquals("", client.uploadDocument(file));
    }

    @Test
    public void testFileDownload() throws Exception {
        var client = new PaperlessClient.Builder()
                .host(System.getenv("PAPERLESS_HOST"))
                .port(System.getenv("PAPERLESS_PORT"))
                .user(System.getenv("PAPERLESS_USER"))
                .password(System.getenv("PAPERLESS_PW"))
                .protocol(System.getenv("PAPERLESS_PROTOCOL"))
                .build();

        File file = client.downloadDocument("236");
        Assertions.assertNotNull(file);
        Assertions.assertTrue(file.exists());
    }

}
