package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.JsonAdapter;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TDocument;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Endpoint for single documents.
 * <b>Endpoint:</b> "/documents/{{id}}/
 * <p>
 * <table border="1">
 *     <caption>Needed path parameters</caption>
 *     <tr>
 *         <th>Param</th>
 *         <th>Description</th>
 *     </tr>
 *     <tr>
 *         <td>id</td>
 *         <td>ID of the document in paperless</td>
 *     </tr>
 * </table>
 * <p>
 * <table border="1">
 *     <caption>Possible query elements</caption>
 *     <tr>
 *         <th>Element</th>
 *         <th>Type</th>
 *         <th>Description</th>
 *         <th>Example</th>
 *     </tr>
 *     <tr>
 *         <td>fields</td>
 *         <td>array</td>
 *         <td>Custom fields the endpoint should return</td>
 *         <td>["Invoice Number","Date"]</td>
 *     </tr>
 * </table>
 * <p>
 * <table border="1">
 *     <caption>Methods</caption>
 *     <tr>
 *         <th>Method</th>
 *         <th>Used for</th>
 *     </tr>
 *     <tr>
 *         <td>GET</td>
 *         <td>Retrieving document with given id</td>
 *     </tr>
 *     <tr>
 *         <td>DELETE</td>
 *         <td>Delete the document with given id</td>
 *     </tr>
 *     <tr>
 *         <td>PATCH</td>
 *         <td>Update given document. Only updates given entries, everything else stays the same</td>
 *     </tr>
 *     <tr>
 *         <td>PUT</td>
 *         <td>Updates given document. Overwrites entire entry of document, earlier information is lost.</td>
 *     </tr>
 * </table>
 */
public class SingleDocumentEndpoint extends ParameterEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(SingleDocumentEndpoint.class);
    /**
     * Allowed methods on this endpoint
     */
    private static final List<HttpMethodC> methods = new ArrayList<>(Arrays.asList(HttpMethodC.PUT, HttpMethodC.GET, HttpMethodC.PATCH, HttpMethodC.DELETE));
    /**
     * Method to use on this endpoint. Should be set via {@link SingleDocumentEndpoint#method(HttpMethodC)} before execution,
     * otherwise an exception will occur
     */
    private HttpMethodC method;
    /**
     * Resource of this endpoint
     */
    private static final String endpoint = "documents/{{id}}/";
    private static final List<String> neededParams = new ArrayList<>(List.of("id"));

    /**
     * Default constructor
     * @param baseUrl Base url of paperless api
     */
    public SingleDocumentEndpoint(@NotNull String baseUrl) {
        super(baseUrl);
    }


    /**
     * {@inheritDoc}
     * @param action
     */
    @Override
    public void action(@NotNull PaperlessActionC action) {
        switch (action) {
            case UPDATE:
                this.method = HttpMethodC.PATCH;
                break;
            case DELETE:
                this.method = HttpMethodC.DELETE;
                break;
            case GET:
                this.method = HttpMethodC.GET;
                break;
            case CREATE_NEW:
                this.method = HttpMethodC.PUT;
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
    public void method(@NotNull HttpMethodC method) {
        if (!methods.contains(method)) throw new IllegalArgumentException("Endpoint does not have given method");
        this.method = method;
    }

    /**
     * {@inheritDoc}
     * @return {@link TDocument} instance of affected document or null, if delete was called.
     * @throws Exception If configured method needs a body, not all path params were given or valid method is configured.
     */
    @Override
    public Request buildRequest() throws Exception {
        if (this.method.equals(HttpMethodC.PATCH) || this.method.equals(HttpMethodC.PUT)) {
            throw new IllegalArgumentException("Calling buildRequest without body not allowed for patch or put operation");
        }
        if (!IParametrizedEndpoint.allPathParamsGiven(this.getParams(), neededParams)) {
            throw new IllegalStateException("Missing path params");
        }
        Request.Builder builder = new Request.Builder()
                .url(this.parseEndpoint(endpoint) + this.parseQuery());
        if (this.method.equals(HttpMethodC.GET)) builder = builder.get();
        else if (this.method.equals(HttpMethodC.DELETE)) builder = builder.delete();
        else {
            this.logger.debug("No valid method given");
            throw new IllegalStateException("Invalid method " + this.method);
        }
        return builder.build();
    }

    /**
     * {@inheritDoc}
     * @param body TDocument instance
     * @return {@link TDocument} instance for the affected document. Null in case of delete.
     * @throws Exception When shit went wrong
     */
    @Override
    public Request buildRequest(@NotNull IDto body) throws Exception {
        switch (this.method) {
            case GET, DELETE:
                return this.buildRequest();
            case PUT, PATCH:
                if (!(body instanceof TDocument)) {
                    throw new IllegalArgumentException("Endpoint can only execute put statement, if an entire TDocument is given");
                }
                Request.Builder builder = new Request.Builder()
                        .url(this.parseEndpoint(endpoint) + this.parseQuery());
                JsonAdapter<TDocument> adapter = this.getMoshi().adapter(TDocument.class);
                String json = adapter.toJson((TDocument) body);
                return (this.method.equals(HttpMethodC.PATCH) ? builder.patch(
                            RequestBody.create(
                                    json,
                                    MediaType.get("application/json;charset=utf-8")
                                )
                            ).build() :
                        builder.put(
                                RequestBody.create(
                                        json,
                                        MediaType.get("application/json;charset=utf-8")
                                )
                        ).build());
            default:
                throw new IllegalStateException("No method set for this endpoint");
        }
    }

    @Override
    public IDto parseResponse(@NotNull Response response) throws Exception {
        if (this.method.equals(HttpMethodC.DELETE)) {
            this.logger.info("Delete method does not yield a body, returning null");
            return null;
        }

        if (response.body() == null) {
            this.logger.info("Request yielded no body");
            throw new RuntimeException("No body yielded");
        }
        JsonAdapter<TDocument> adapter = this.getMoshi().adapter(TDocument.class);
        return adapter.fromJson(response.body().string());
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<HttpMethodC> getMethods() {
        return methods;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getPathParams() {
        return neededParams;
    }
}
