package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import okhttp3.Request;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TDocument;
import org.dmsextension.paperless.client.templates.TSpecifiedSearchResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Endpoint:</b> "/api/documents/"
 */
public class DocumentEndpoint extends PaperlessEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(DocumentEndpoint.class);
    /**
     * Endpoint
     */
    private static final String endpoint = "documents/";
    /**
     * Allowed methods on this endpoint
     */
    private static final List<HttpMethodC> methods = new ArrayList<>(List.of(HttpMethodC.GET));
    /**
     * Method to execute
     */
    @SuppressWarnings("unused")
    private HttpMethodC method;
    /**
     * Default constructor
     *
     * @param baseUrl Base url for paperless ngx api
     */
    public DocumentEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * @return Get request to query the endpoint
     * @throws Exception Probably not thrown
     */
    @Override
    public Request buildRequest() throws Exception {
        return new Request.Builder().url(this.getBaseUrl() + endpoint + this.parseQuery()).get().build();
    }

    /**
     * {@inheritDoc}
     * @param body Body to send. If configured operation of the endpoint does not require a body, {@link IEndpoint#buildRequest()}
     *             is called instead
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest(@NotNull IDto body) throws Exception {
        return this.buildRequest();
    }

    /**
     * {@inheritDoc}
     * @param response Response of the api call
     * @return Body of the response parsed into a {@link TSpecifiedSearchResult<TDocument>} instance
     * @throws Exception If response does not have a body
     */
    @Override
    public TSpecifiedSearchResult<TDocument> parseResponse(@NotNull Response response) throws Exception {
        if (response.body() == null) {
            throw new IllegalStateException("Response does not have a body");
        }
        Type type = Types.newParameterizedType(TSpecifiedSearchResult.class, TDocument.class);
        JsonAdapter<TSpecifiedSearchResult<TDocument>> adapter = this.getMoshi().adapter(type);
        return adapter.fromJson(response.body().string());
    }

    /**
     * {@inheritDoc}
     * @return Possible methods for this endpoint
     */
    @Override
    public List<HttpMethodC> getMethods() {
        return methods;
    }

    /**
     * {@inheritDoc}
     * @param action Action the endpoint should execute
     */
    @Override
    public void action(@NotNull PaperlessActionC action) {
        this.logger.info("Endpoint" + this + " only supports GET, method is irrelevant");
    }

    /**
     * {@inheritDoc}
     * @param method HTTP Method
     */
    @Override
    public void method(@NotNull HttpMethodC method) {
        this.logger.info("Endpoint" + this + " only supports GET, method is irrelevant");
    }
}
