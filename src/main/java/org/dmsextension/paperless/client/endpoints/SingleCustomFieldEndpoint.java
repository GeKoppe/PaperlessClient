package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.JsonAdapter;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TCustomFieldTemplate;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Endpoint to fetch, update or delete custom fields in paperless-ngx dms.
 * <p>
 * <b>Endpoint:</b> "/custom_fields/{{id}}/
 * <p>
 * <table border="1">
 *     <caption>Needed path parameters</caption>
 *     <tr>
 *         <th>Param</th>
 *         <th>Description</th>
 *     </tr>
 *     <tr>
 *         <td>id</td>
 *         <td>ID of the custom field in paperless</td>
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
 *         <td>Retrieving custom field with given id</td>
 *     </tr>
 *     <tr>
 *         <td>DELETE</td>
 *         <td>Delete the custom field with given id</td>
 *     </tr>
 *     <tr>
 *         <td>PATCH</td>
 *         <td>Update given custom field. Only updates given entries, everything else stays the same</td>
 *     </tr>
 *     <tr>
 *         <td>PUT</td>
 *         <td>Updates given custom field. Overwrites entire entry of custom field, earlier information is lost.</td>
 *     </tr>
 * </table>
 */
public class SingleCustomFieldEndpoint extends ParameterEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(SingleCustomFieldEndpoint.class);
    /**
     * Endpoint
     */
    private static final String endpoint = "custom_fields/{{id}}/";
    /**
     * Method used when executing this endpoint
     */
    private HttpMethodC method;
    /**
     * List of all allowed methods on this endpoint
     */
    private final List<HttpMethodC> methods = new ArrayList<>(
            List.of(HttpMethodC.GET, HttpMethodC.DELETE, HttpMethodC.PATCH, HttpMethodC.PUT)
    );
    /**
     * URL params needed for the endpoint
     */
    private static final List<String> neededParams = new ArrayList<>(List.of("id"));

    /**
     * Default constructor
     * @param baseUrl Base url of paperless ngx api
     */
    public SingleCustomFieldEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * Method can only be used, if the endpoint is configured with get or delete
     * @return Built request
     * @throws Exception If wrong method is configured, not all path params are given or path can't be parsed
     */
    @Override
    public Request buildRequest() throws Exception {
        if (this.method.equals(HttpMethodC.PATCH) || this.method.equals(HttpMethodC.PUT)) {
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
        if (this.method.equals(HttpMethodC.GET)) builder = builder.get();
        else builder = builder.delete();

        return builder.build();
    }

    @Override
    public Request buildRequest(@NotNull IDto body) throws Exception {
        if (this.method.equals(HttpMethodC.GET) || this.method.equals(HttpMethodC.DELETE)) {
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

        String url = this.parseEndpoint(endpoint) + this.parseQuery();
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (this.method.equals(HttpMethodC.PATCH)) builder = builder.patch(rBody);
        else builder = builder.put(rBody);

        return builder.build();
    }

    /**
     * {@inheritDoc}
     * @param response Response of the api call
     * @return {@link TCustomFieldTemplate} instance parsed from response body
     * @throws Exception Thrown, if response has no body on any other method than delete or if
     * parsing results in an exception.
     */
    @Override
    public TCustomFieldTemplate parseResponse(@NotNull Response response) throws Exception {
        if (this.method.equals(HttpMethodC.DELETE)) {
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

        JsonAdapter<TCustomFieldTemplate> adapter = this.getMoshi().adapter(TCustomFieldTemplate.class);
        TCustomFieldTemplate result = adapter.fromJson(response.body().string());
        this.logger.debug(String.format("Parsed to %s", result));

        return result;
    }

    /**
     * {@inheritDoc}
     * @return Methods this endpoint allows
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
        switch (action) {
            case GET:
                this.method = HttpMethodC.GET;
                break;
            case DELETE:
                this.method = HttpMethodC.DELETE;
                break;
            case UPDATE:
                this.method = HttpMethodC.PATCH;
                break;
            default:
                this.logger.info(String.format("No operation fits the action %s", action));
                break;
        }
    }

    /**
     * {@inheritDoc}
     * @param method HTTP Method
     */
    @Override
    public void method(@NotNull HttpMethodC method) {
        if (!methods.contains(method)) {
            this.logger.debug(String.format("Method %s not supported on endpoint %s", method, this));
            throw new IllegalArgumentException(String.format("Method %s not supported on endpoint %s", method, this));
        }
        this.method = method;
    }

    /**
     * {@inheritDoc}
     * @return Params this endpoint needs in it's path
     */
    @Override
    public List<String> getPathParams() {
        return neededParams;
    }
}
