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

public class DocumentUploadEndpoint extends PaperlessEndpoint implements IEndpoint {
    private final Logger logger = LoggerFactory.getLogger(DocumentUploadEndpoint.class);
    private static final List<MethodC> methods = new ArrayList<>(Arrays.asList(MethodC.POST));
    private MethodC method;
    private static final String endpoint = "documents/post_document";

    public DocumentUploadEndpoint(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public void action(@NotNull ActionC action) {

    }

    @Override
    public void method(@NotNull MethodC method) {

    }

    @Override
    public List<MethodC> getMethods() {
        return methods;
    }

    @Override
    public Request buildRequest() throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot call build request without body on this endpoint");
    }

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

    @Override
    public TDocumentUploadResponse parseResponse(@NotNull Response response) throws IOException {
        var r = new TDocumentUploadResponse();
        var body = response.body();
        if (body != null) r.setGuid(body.string());
        else r.setGuid("");
        return r;
    }

    public MethodC getMethod() {
        return method;
    }
}
