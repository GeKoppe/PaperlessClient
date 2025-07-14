package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.Moshi;

public abstract class PaperlessEndpoint {
    private String baseUrl;
    private final Moshi moshi = new Moshi.Builder().build();
    public PaperlessEndpoint(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected Moshi getMoshi() {
        return this.moshi;
    }
}
