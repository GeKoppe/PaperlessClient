package org.dmsextension.paperless.client.endpoints;

import com.squareup.moshi.JsonAdapter;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TSearchResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Endpoint for doing basic searches in paperless. Supports the following query params:
 * <table border="1">
 *     <caption>Query parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Type</th>
 *         <th>Value</th>
 *     </tr>
 *     <tr>
 *         <td>db_only</td>
 *         <td>boolean</td>
 *         <td>Defines, if paperless should only search the database</td>
 *     </tr>
 *     <tr>
 *         <td>query</td>
 *         <td>String</td>
 *         <td>Everything you want to search for, pressed into one string. Don't ask me, I don't like it either.</td>
 *     </tr>
 * </table>
 */
public class SearchEndpoint extends PaperlessEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(SearchEndpoint.class);
    /**
     * Methods this endpoint supports
     */
    private static final List<HttpMethodC> methods = new ArrayList<>(List.of(HttpMethodC.GET));
    private static final String endpoint = "search/";
    /**
     * Default constructor
     * @param baseUrl Base url for paperless api
     */
    public SearchEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest() throws Exception {
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
        this.logger.debug("This endpoint only supports GET operations, no body needed.");
        return this.buildRequest();
    }

    /**
     * {@inheritDoc}
     * @param response Response of the api call
     * @return
     * @throws Exception
     */
    @Override
    public TSearchResult parseResponse(@NotNull Response response) throws Exception {
        ResponseBody body = response.body();
        if (body == null) {
            this.logger.info("No body was returned, cannot parse");
            throw new IllegalArgumentException("No body given");
        }
        JsonAdapter<TSearchResult> adapter = this.getMoshi().adapter(TSearchResult.class);

        return adapter.fromJson(body.string());
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
     * This particular endpoint only supports GET, therefore this method is obsolete.
     * @param action Action the endpoint should execute
     */
    @Override
    public void action(@NotNull PaperlessActionC action) {
    }

    /**
     * {@inheritDoc}
     * This particular endpoint only supports GET, therefore this method is obsolete.
     * @param method HTTP Method
     */
    @Override
    public void method(@NotNull HttpMethodC method) {
    }
}
