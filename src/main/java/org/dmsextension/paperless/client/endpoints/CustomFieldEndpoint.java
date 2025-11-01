package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TCustomFieldTemplate;
import org.dmsextension.paperless.client.templates.TSpecifiedSearchResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Endpoint to retrieve all custom fields or create a new one
 */
public class CustomFieldEndpoint extends PaperlessEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(CustomFieldEndpoint.class);
    /**
     * Allowed methods on this endpoint
     */
    private static final List<HttpMethodC> methods = new ArrayList<>(List.of(HttpMethodC.GET, HttpMethodC.POST));
    /**
     * Resource for this endpoint
     */
    private static final String endpoint = "custom_fields/";
    private HttpMethodC method;

    /**
     * Default constructor
     * @param baseUrl Base url of paperless api
     */
    public CustomFieldEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * @return Built request
     * @throws Exception If request building failed
     */
    @Override
    public Request buildRequest() throws Exception {
        if (this.method.equals(HttpMethodC.POST)) {
            this.logger.info("Post method not allowed without giving a body");
            throw new IllegalStateException("Post method not allowed without a body");
        }
        String completedUlr = this.getBaseUrl() + endpoint;

        return new Request.Builder()
                .url(completedUlr + this.parseQuery())
                .header("Content-Type", "application/json")
                .get()
                .build();
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
        if (this.method.equals(HttpMethodC.GET)) {
            this.logger.info("Body not needed when using get, deferring to " + this.getClass().getMethod("buildRequest"));
            return buildRequest();
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

        String url = this.getBaseUrl() + endpoint + this.parseQuery();
        Request.Builder builder = new Request.Builder()
                .url(url);
        builder = builder.post(rBody);

        return builder.build();
    }

    @Override
    public IDto parseResponse(@NotNull Response response) throws Exception {
        if (response.body() == null) {
            this.logger.info("No body in response, returning");
            return null;
        }
        IDto res;
        if (this.method.equals(HttpMethodC.GET)) {
            Type type = Types.newParameterizedType(TSpecifiedSearchResult.class, TCustomFieldTemplate.class);
            JsonAdapter<TSpecifiedSearchResult<TCustomFieldTemplate>> adapter = this.getMoshi().adapter(type);
            res = adapter.fromJson(response.body().string());
        } else {
            JsonAdapter<TCustomFieldTemplate> adapter = this.getMoshi().adapter(TCustomFieldTemplate.class);
            res = adapter.fromJson(response.body().string());
        }
        return res;
    }

    @Override
    public List<HttpMethodC> getMethods() {
        return methods;
    }

    @Override
    public void action(@NotNull PaperlessActionC action) {
        this.logger.info("Method not supported on this endpoint");
    }

    @Override
    public void method(@NotNull HttpMethodC method) {
        if (!methods.contains(method)) {
            this.logger.info("Method {} not allowed on endpoint {}", method, this);
            throw new IllegalArgumentException("Method " + method + " not allowed on endpoint " + this);
        }
        this.method = method;
    }
}
