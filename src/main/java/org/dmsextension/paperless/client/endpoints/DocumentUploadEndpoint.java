package org.dmsextension.paperless.client.endpoints;

import okhttp3.Request;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.ActionC;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TDocumentUpload;
import org.dmsextension.paperless.client.templates.TDocumentUploadResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Endpoint for uploading documents to paperless-ngx
 */
public class DocumentUploadEndpoint extends PaperlessEndpoint implements IEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(DocumentUploadEndpoint.class);
    /**
     * List of all methods
     */
    private static final List<MethodC> methods = new ArrayList<>(Arrays.asList(MethodC.POST));
    /**
     * Method to execute. Set to {@link MethodC#POST} by default, because endpoint only allows post
     */
    private MethodC method = MethodC.POST;
    /**
     * Endpoint resource
     */
    private static final String endpoint = "documents/post_document";

    /**
     * Default constructor
     * @param baseUrl Base url of paperless api
     */
    public DocumentUploadEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * @param action Action the endpoint should execute
     */
    @Override
    public void action(@NotNull ActionC action) {

    }

    /**
     * {@inheritDoc}
     * @param method HTTP Method
     */
    @Override
    public void method(@NotNull MethodC method) {
        this.method = method;
    }

    /**
     * {@inheritDoc}
     * @return Methods for this endpoint
     */
    @Override
    public List<MethodC> getMethods() {
        return methods;
    }

    /**
     * {@inheritDoc}
     * @return
     * @throws IllegalArgumentException Always throws, as this endpoint only allows post and therefore cannot operate
     * without body to send.
     */
    @Override
    public Request buildRequest() throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot call build request without body on this endpoint");
    }

    /**
     * {@inheritDoc}
     * @param body Body to send. If configured operation of the endpoint does not require a body, {@link IEndpoint#buildRequest()}
     *             is called instead
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Request buildRequest(@NotNull IDto body) throws IllegalArgumentException{
        if (!(body instanceof TDocumentUpload) || !methods.contains(this.method)) {
            this.logger.info(String.format("Invalid configuration given. Body is of type %s, needed %s", body.getClass(), TDocumentUpload.class));
            throw new IllegalArgumentException(
                    String.format(
                        "Invalid body given. Body is of type %s, needed %s",
                            body.getClass(),
                            TDocumentUpload.class
                    )
            );
        }

        return new Request.Builder()
                .url(this.getBaseUrl() + "documents/post_document/")
                .post(((TDocumentUpload) body).getRequestBody())
                .build();
    }

    /**
     * {@inheritDoc}
     * @param response Response of the api call
     * @return
     * @throws IOException
     */
    @Override
    public TDocumentUploadResponse parseResponse(@NotNull Response response) throws IOException {
        var r = new TDocumentUploadResponse();
        var body = response.body();
        if (body != null) r.setGuid(body.string());
        else r.setGuid("");
        return r;
    }

    /**
     * Get configured method.
     * @return Configured method for endpoint
     */
    public MethodC getMethod() {
        return method;
    }
}
