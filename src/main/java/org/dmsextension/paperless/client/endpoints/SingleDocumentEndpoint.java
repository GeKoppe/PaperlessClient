package org.dmsextension.paperless.client.endpoints;

import okhttp3.Request;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.ActionC;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TDocument;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SingleDocumentEndpoint extends ParameterEndpoint implements IParametrizedEndpoint {
    private final Logger logger = LoggerFactory.getLogger(SingleDocumentEndpoint.class);
    private static final List<MethodC> methods = new ArrayList<>(Arrays.asList(MethodC.PUT, MethodC.GET, MethodC.PATCH, MethodC.DELETE));
    private MethodC method;
    private static final String endpoint = "documents/{{id}}/";
    public SingleDocumentEndpoint(String baseUrl) {
        super(baseUrl);
    }


    /**
     * {@inheritDoc}
     * @param action
     */
    @Override
    public void action(@NotNull ActionC action) {
        switch (action) {
            case UPDATE:
                this.method = MethodC.PATCH;
                break;
            case DELETE:
                this.method = MethodC.DELETE;
                break;
            case GET:
                this.method = MethodC.GET;
                break;
            default:
                throw new IllegalArgumentException("Endpoint can only update, get or delete document");
        }
    }

    /**
     * {@inheritDoc}
     * @param method
     */
    @Override
    public void method(@NotNull MethodC method) {
        if (!methods.contains(method)) throw new IllegalArgumentException("Endpoint does not have given method");
        this.method = method;
    }

    /**
     * {@inheritDoc}
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest() throws Exception {
        if (this.method.equals(MethodC.PATCH) || this.method.equals(MethodC.PUT)) {
            throw new IllegalArgumentException("Calling buildRequest without body not allowed for patch or put operation");
        }
        Request.Builder builder = new Request.Builder();

        return builder.build();
    }

    /**
     * {@inheritDoc}
     * @param body
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest(@NotNull IDto body) throws Exception {
        switch (this.method) {
            case GET, DELETE:
                return this.buildRequest();
            case PUT:
                if (!(body instanceof TDocument)) {
                    throw new IllegalArgumentException("Endpoint can only execute put statement, if an entire TDocument is given");
                }
                return null;
            case PATCH:

            default:
                throw new IllegalStateException("No method set for this endpoint");
        }
    }

    @Override
    public IDto parseResponse(@NotNull Response response) throws Exception {
        return null;
    }

    @Override
    public List<MethodC> getMethods() {
        return null;
    }

    @Override
    public List<String> getParameters() {
        return null;
    }

    @Override
    public void urlParams(Map<String, String> params) {

    }
}
