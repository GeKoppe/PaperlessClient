package org.dmsextension.paperless.client.endpoints;

import okhttp3.Request;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implemented by all endpoint classes.
 */
public interface IEndpoint {
    /**
     * Method used for building request, that does not require a body. If this method is called for a
     * {@link PaperlessEndpoint} instance with a method that requires a body, usually an exception is thrown.
     * @return The built request
     * @throws Exception When this method is called for operations that require a body
     */
    Request buildRequest() throws Exception;

    /**
     * Builds the request object depending on the configuration of the endpoint instance.
     * @param body Body to send. If configured operation of the endpoint does not require a body, {@link IEndpoint#buildRequest()}
     *             is called instead
     * @return Generated request object
     * @throws Exception Thrown if instance is not configured correctly
     */
    Request buildRequest(@NotNull IDto body) throws Exception;

    /**
     * Parses response object to paperless {@link IDto} object
     * @param response Response of the api call
     * @return Parsed response
     * @throws Exception Thrown due to various reasons
     */
    IDto parseResponse(@NotNull Response response) throws Exception;

    /**
     * Get a list of all possible methods for endpoint instance
     * @return List of all methods
     */
    List<HttpMethodC> getMethods();

    /**
     * Convenience method for deciding on a http method for the endpoint.
     * @param action Action the endpoint should execute
     */
    void action(@NotNull PaperlessActionC action);

    /**
     * Explicitly set the http method the endpoint should execute
     * @param method HTTP Method
     */
    void method(@NotNull HttpMethodC method);
    // IDto bodyFromParams(HashMap<String, Object> params);

    /**
     * Adds the url query to the endpoint
     * @param query
     */
    void query(@NotNull Map<String, String> query);
}
