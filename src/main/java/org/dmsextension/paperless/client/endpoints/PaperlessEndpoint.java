package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Base class containing all necessary tools for building and parsing a paperless ngx endpoint
 */
public abstract class PaperlessEndpoint implements IEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(PaperlessEndpoint.class);
    /**
     * Base url for paperless ngx
     */
    private String baseUrl;
    /**
     * Moshi for parsing json bodies to objects
     */
    private final Moshi moshi = new Moshi.Builder().build();
    /**
     * Http query parameters
     */
    private Map<String, String> query;

    /**
     * Default constructor
     * @param baseUrl Base url for paperless ngx api
     */
    public PaperlessEndpoint(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Returns base url for paperless ngx api
     * @return Base url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets base url for the paperless ngx api
     * @param baseUrl Base url for paperless ngx api
     */
    public void setBaseUrl(@NotNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Gets {@link Moshi} of this instance
     * @return Moshi of this instance
     */
    protected Moshi getMoshi() {
        return this.moshi;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @NotNull
    protected final String parseQuery() {
        StringBuilder query = new StringBuilder();
        if (this.query == null) {
            this.logger.info("No query given to this endpoint");
            return "";
        }
        boolean firstAdded = false;
        for (var k : this.query.keySet()) {
            if (firstAdded) query.append("&");
            query.append(k)
                    .append("=")
                    .append(URLEncoder.encode(this.query.get(k), StandardCharsets.UTF_8));
            firstAdded = true;
        }
        return query.toString();
    }

    /**
     * Sets endpoint query params
     * @param query Query for the request
     */
    public void query(Map<String, String> query) {
        this.query = query;
    }
}
