package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.JsonAdapter;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.ActionC;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TCustomFieldTemplate;
import org.dmsextension.paperless.client.templates.TStoragePath;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Endpoint to fetch, update or delete custom fields in paperless-ngx dms.
 * <p>
 * <b>Endpoint:</b> "/storage_paths/{{id}}/
 * <p>
 * <table border="1">
 *     <caption>Needed path parameters</caption>
 *     <tr>
 *         <th>Param</th>
 *         <th>Description</th>
 *     </tr>
 *     <tr>
 *         <td>id</td>
 *         <td>ID of the storage path in paperless</td>
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
 *         <td>Retrieving storage path with given id</td>
 *     </tr>
 *     <tr>
 *         <td>DELETE</td>
 *         <td>Delete the storage path with given id</td>
 *     </tr>
 *     <tr>
 *         <td>PATCH</td>
 *         <td>Update given storage path. Only updates given entries, everything else stays the same</td>
 *     </tr>
 *     <tr>
 *         <td>PUT</td>
 *         <td>Updates given storage path. Overwrites entire entry of storage path, earlier information is lost.</td>
 *     </tr>
 * </table>
 */
public class SingleStoragepathEndpoint extends ParameterEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(SingleCustomFieldEndpoint.class);
    /**
     * Endpoint
     */
    private static final String endpoint = "storage_paths/{{id}}/";
    /**
     * Method used when executing this endpoint
     */
    private MethodC method;
    /**
     * List of all allowed methods on this endpoint
     */
    private final List<MethodC> methods = new ArrayList<>(
            List.of(MethodC.GET, MethodC.DELETE, MethodC.PATCH, MethodC.PUT)
    );
    /**
     * URL params needed for the endpoint
     */
    private static final List<String> neededParams = new ArrayList<>(List.of("id"));
    public SingleStoragepathEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest() throws Exception {
        if (this.method.equals(MethodC.PATCH) || this.method.equals(MethodC.PUT)) {
            this.logger.info(String.format("Cannot execute %s on endpoint %s without body", this.method, this));
            throw new IllegalStateException(String.format("Cannot execute %s on endpoint %s without body", this.method, this));
        }
        if (!IParametrizedEndpoint.allPathParamsGiven(this.getParams(), neededParams)) {
            this.logger.info(String.format("Missing params of %s", neededParams));
            throw new IllegalStateException(String.format("Missing params of %s", neededParams));
        }

        String url = this.parseEndpoint(endpoint);
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (this.method.equals(MethodC.GET)) builder = builder.get();
        else builder = builder.delete();

        return builder.build();
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
        if (this.method.equals(MethodC.GET) || this.method.equals(MethodC.DELETE)) {
            this.logger.debug(String.format("Endpoint is configured with %s, ignoring body", this.method));
            return this.buildRequest();
        }
        if (!IParametrizedEndpoint.allPathParamsGiven(this.getParams(), neededParams)) {
            this.logger.info(String.format("Missing params of %s", neededParams));
            throw new IllegalStateException(String.format("Missing params of %s", neededParams));
        }
        if (!(body instanceof TCustomFieldTemplate)) {
            this.logger.info(String.format("Body is not instance of %s, cannot for this endpoint", TCustomFieldTemplate.class));
            throw new IllegalArgumentException(String.format("Body is not instance of %s, cannot for this endpoint", TCustomFieldTemplate.class));
        }

        JsonAdapter<TCustomFieldTemplate> adapter = this.getMoshi().adapter(TCustomFieldTemplate.class);
        String json = adapter.toJson((TCustomFieldTemplate) body);
        this.logger.debug(String.format("Compiled given body to json: %s", json));

        RequestBody rBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        this.logger.debug(String.format("Built request body %s", rBody));

        String url = this.parseEndpoint(endpoint);
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (this.method.equals(MethodC.PATCH)) builder = builder.patch(rBody);
        else builder = builder.put(rBody);

        return builder.build();
    }

    /**
     * {@inheritDoc}
     * @param response Response of the api call
     * @return
     * @throws Exception
     */
    @Override
    public TStoragePath parseResponse(@NotNull Response response) throws Exception {
        if (this.method.equals(MethodC.DELETE)) {
            this.logger.debug("Method DELETE does not have a response body, returning null");
            return null;
        }
        if (!response.isSuccessful()) {
            this.logger.debug("Request was not successful, returning null");
            return null;
        }
        if (response.body() == null) {
            this.logger.info(String.format("No body in response %s", response));
            throw new IllegalStateException(String.format("No body in response %s", response));
        }
        this.logger.debug(String.format("Parsing body %s to custom field template", response));

        JsonAdapter<TStoragePath> adapter = this.getMoshi().adapter(TStoragePath.class);
        TStoragePath result = adapter.fromJson(response.body().string());
        this.logger.debug(String.format("Parsed to %s", result));

        return result;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<MethodC> getMethods() {
        return methods;
    }

    /**
     * {@inheritDoc}
     * @param action Action the endpoint should execute
     */
    @Override
    public void action(@NotNull ActionC action) {
        switch (action) {
            case GET:
                this.method = MethodC.GET;
                break;
            case DELETE:
                this.method = MethodC.DELETE;
                break;
            case UPDATE:
                this.method = MethodC.PATCH;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     * @param method HTTP Method
     */
    @Override
    public void method(@NotNull MethodC method) {
        if (!methods.contains(method)) {
            this.logger.info(String.format("Method %s is not allowed on endpoint %s", method, this));
            throw new IllegalArgumentException(String.format("Method %s is not allowed on endpoint %s", method, this));
        }
        this.method = method;
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
