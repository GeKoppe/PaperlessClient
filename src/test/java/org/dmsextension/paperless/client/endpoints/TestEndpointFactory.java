package org.dmsextension.paperless.client.endpoints;

import org.dmsextension.paperless.client.PaperlessClient;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TDocumentDownload;
import org.dmsextension.paperless.client.templates.TDocumentUpload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class TestEndpointFactory {
    private final PaperlessClient client = new PaperlessClient.Builder()
            .host(System.getenv("PAPERLESS_HOST"))
            .port(System.getenv("PAPERLESS_PORT"))
            .user(System.getenv("PAPERLESS_USER"))
            .password(System.getenv("PAPERLESS_PW"))
            .protocol(System.getenv("PAPERLESS_PROTOCOL"))
            .build();

    @Test
    public void testDocumentDownloadEndpoint() throws Exception {
        var test = EndpointFactory.documentDownloadEndpoint(client.getUrl());
        Map<String, String> params =  new HashMap<>(Map.of("id", "236"));
        test.urlParams(params);
        try {
            IDto dto = client.execute(test, null);
            Assertions.assertInstanceOf(TDocumentDownload.class, dto);
            TDocumentDownload download = (TDocumentDownload) dto;
            Assertions.assertNotNull(download.getFile());
            Assertions.assertTrue(download.getFile().exists());
            if (download.getFile().exists()) download.getFile().delete();
        } catch (Exception ex) {
            fail();
        }

    }
}
