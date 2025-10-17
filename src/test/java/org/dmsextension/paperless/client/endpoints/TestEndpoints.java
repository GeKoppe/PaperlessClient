package org.dmsextension.paperless.client.endpoints;

import org.dmsextension.paperless.client.PaperlessClient;
import org.dmsextension.paperless.client.http.ActionC;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class TestEndpoints {
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
        test.pathParams(params);
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

    @Test
    public void testSearchEndpoint() {
        SearchEndpoint ep = new SearchEndpoint(this.client.getUrl());
        ep.query(Map.of("query", "Rechnung"));
        try {
            IDto docs = this.client.execute(ep, null);
            Assertions.assertInstanceOf(TSearchResult.class, docs);
            TSearchResult result = (TSearchResult) docs;
            Assertions.assertTrue(result.getTotal() > 0);
            Assertions.assertFalse(result.getWorkflows().isEmpty());
            Assertions.assertFalse(result.getStoragePaths().isEmpty());
            // Assertions.assertFalse(result.getSavedViews().isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testSingleDocumentEndpoint() {
        SingleDocumentEndpoint ep = new SingleDocumentEndpoint(this.client.getUrl());
        ep.pathParams(Map.of("id", "258"));
        try {
            ep.action(ActionC.GET);
            IDto document = this.client.execute(ep, null);
            Assertions.assertInstanceOf(TDocument.class, document);
            TDocument doc = (TDocument) document;
            Assertions.assertEquals(258, doc.getId());
            Assertions.assertFalse(doc.getCustomFields().isEmpty());
        } catch (Exception ex) {
            fail(ex);
        }
    }
    @Test
    public void testEndpointWithQuery() {
        DocumentEndpoint ep = EndpointFactory.documentEndpoint(client.getUrl());

        Map<String, String> query = new HashMap<>();
        query.put("ordering", "id");
        ep.query(query);

        TSpecifiedSearchResult<TDocument> docs = null;
        try {
            IDto result = client.execute(ep, null);
            if (!(result instanceof TSpecifiedSearchResult<?>)) {
                fail();
                return;
            }
            docs = (TSpecifiedSearchResult<TDocument>) result;
        } catch (Exception ex) {
            fail(ex);
        }
        Assertions.assertTrue(docs.getCount() > 0);
    }

    @Test
    public void testCustomfieldEndpoint() {
        CustomFieldEndpoint ep = new CustomFieldEndpoint(client.getUrl());
        Map<String, String> query = new HashMap<>();
        query.put("name__istartswith", "Rechnungs");
        ep.query(query);
        ep.method(MethodC.GET);
        IDto res = null;
        try {
            res = client.execute(ep, null);
            if (!(res instanceof TSpecifiedSearchResult<?>)) {
                fail();
                return;
            }
            @SuppressWarnings("unchecked")
            TSpecifiedSearchResult<TCustomFieldTemplate> cft = (TSpecifiedSearchResult<TCustomFieldTemplate>) res;
            Assertions.assertTrue(cft.getResults().size() > 0);
        } catch (Exception ex) {
            fail(ex);
        }
    }
}
