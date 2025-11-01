package org.dmsextension.paperless.client.utils;

import com.github.jknack.handlebars.Template;
import org.dmsextension.paperless.client.PaperlessClient;
import org.dmsextension.paperless.client.endpoints.EndpointFactory;
import org.dmsextension.paperless.client.endpoints.SingleDocumentEndpoint;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.TCustomFieldTemplate;
import org.dmsextension.paperless.client.templates.TCustomFieldValue;
import org.dmsextension.paperless.client.templates.TDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class TestCustomHandlebars {
    private final CustomHandlebars ch = new CustomHandlebars();

    @Test
    public void testPaperlessReplace() throws IOException {
        Template t = ch.compileInline("{{ custom_fields|get_cf_value(\"Rechnungssteller\") }}");
        Assertions.assertEquals("{{get_cf_value custom_fields \"Rechnungssteller\"}}", t.text());

        t = ch.compileInline("{{ custom_fields|get_cf_value(\"Belegdatum\",\"2024-01-01\")|datetime('%Y') }}");
        Assertions.assertEquals("{{datetime (get_cf_value custom_fields \"Belegdatum\" \"2024-01-01\") \"%Y\"}}", t.text());

        t = ch.compileInline("Rechnungen/{{ custom_fields|get_cf_value(\"Rechnungssteller\")}}/{{ custom_fields|get_cf_value(\"Belegdatum\",\"2024-01-01\")|datetime('%Y') }}/{{ custom_fields|get_cf_value(\"Belegdatum\",\"2024-01-01\")|datetime('%m') }}/{{ custom_fields|get_cf_value(\"Rechnungssteller\")}} - {{ custom_fields|get_cf_value(\"Ort\")}} - {{ custom_fields|get_cf_value(\"Belegdatum\")}}");
        Assertions.assertEquals("Rechnungen/{{get_cf_value custom_fields \"Rechnungssteller\"}}/{{datetime (get_cf_value custom_fields \"Belegdatum\" \"2024-01-01\") \"%Y\"}}/{{datetime (get_cf_value custom_fields \"Belegdatum\" \"2024-01-01\") \"%m\"}}/{{get_cf_value custom_fields \"Rechnungssteller\"}} - {{get_cf_value custom_fields \"Ort\"}} - {{get_cf_value custom_fields \"Belegdatum\"}}", t.text());
    }

    @Test
    public void testDatetimeHelper() throws IOException {
        Template t = ch.compileInline("{{datetime \"2024-01-01\" \"%Y\"}}");
        String result = t.apply(new Object());
        Assertions.assertEquals("2024", result);

        t = ch.compileInline("{{datetime \"2024-01-01\" \"%m\"}}");
        result = t.apply(new Object());
        Assertions.assertEquals("01", result);

        t = ch.compileInline("{{datetime \"2024-01-01\" \"%D\"}}");
        result = t.apply(new Object());
        Assertions.assertEquals("01", result);
    }

    @Test
    public void testToMap() {
        try {
            PaperlessClient client = new PaperlessClient.Builder()
                    .host(System.getenv("PAPERLESS_HOST"))
                    .port(System.getenv("PAPERLESS_PORT"))
                    .user(System.getenv("PAPERLESS_USER"))
                    .password(System.getenv("PAPERLESS_PW"))
                    .protocol(System.getenv("PAPERLESS_PROTOCOL"))
                    .build();

            SingleDocumentEndpoint ep = new SingleDocumentEndpoint(client.getUrl());
            ep.pathParams(Map.of("id", "250"));
            ep.method(HttpMethodC.GET);
            TDocument doc = (TDocument) client.execute(ep, null);

            Map<String, Object> map = CustomHandlebars.convertToMap(doc, TDocument.class);
            Assertions.assertEquals(250D, map.get("id"));
        } catch (Exception ex) {
            fail(ex);
        }
    }

    @Test
    public void testPaperlessCfReplace() {
        PaperlessClient client = new PaperlessClient.Builder()
                .host(System.getenv("PAPERLESS_HOST"))
                .port(System.getenv("PAPERLESS_PORT"))
                .user(System.getenv("PAPERLESS_USER"))
                .password(System.getenv("PAPERLESS_PW"))
                .protocol(System.getenv("PAPERLESS_PROTOCOL"))
                .build();
        SingleDocumentEndpoint ep = new SingleDocumentEndpoint(client.getUrl());
        ep.pathParams(Map.of("id", "250"));
        ep.method(HttpMethodC.GET);
        TDocument doc;
        try {
            doc = (TDocument) client.execute(ep, null);
        } catch (Exception e) {
            fail(e);
            return;
        }

        List<TCustomFieldValue> values = doc.getCustomFields();
        CustomHandlebars.getSelectFieldValues(values, client);
        Assertions.assertEquals("Automatisiert", values.get(4).getValue());
    }

    @Test
    public void testCfMapping() throws IOException {
        PaperlessClient client = new PaperlessClient.Builder()
                .host(System.getenv("PAPERLESS_HOST"))
                .port(System.getenv("PAPERLESS_PORT"))
                .user(System.getenv("PAPERLESS_USER"))
                .password(System.getenv("PAPERLESS_PW"))
                .protocol(System.getenv("PAPERLESS_PROTOCOL"))
                .build();
        SingleDocumentEndpoint ep = new SingleDocumentEndpoint(client.getUrl());
        ep.pathParams(Map.of("id", "250"));
        ep.method(HttpMethodC.GET);
        TDocument doc;
        try {
            doc = (TDocument) client.execute(ep, null);
        } catch (Exception e) {
            fail(e);
            return;
        }
        Template t = ch.compileInline("Rechnungen/{{ custom_fields|get_cf_value(\"Rechnungssteller\")}}/{{ custom_fields|get_cf_value(\"Belegdatum\",\"2024-01-01\")|datetime('%Y') }}/{{ custom_fields|get_cf_value(\"Belegdatum\",\"2024-01-01\")|datetime('%m') }}/{{ custom_fields|get_cf_value(\"Rechnungssteller\")}} - {{ custom_fields|get_cf_value(\"Ort\")}} - {{ custom_fields|get_cf_value(\"Belegdatum\")}}");
        CustomHandlebars.getSelectFieldValues(doc.getCustomFields(), client);
        Map<String, Object> map = CustomHandlebars.convertToMap(doc, TDocument.class);

        String done = t.apply(map);
        Assertions.assertEquals("Rechnungen/Vodafone Deutschland GmbH/2025/01/Vodafone Deutschland GmbH - Erfurt - 2025-01-17", done);
    }
}
