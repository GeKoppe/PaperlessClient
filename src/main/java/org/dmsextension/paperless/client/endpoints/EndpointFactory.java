package org.dmsextension.paperless.client.endpoints;

import org.dmsextension.paperless.client.http.HttpMethodC;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory that builds {@link IEndpoint} objects.
 */
public class EndpointFactory {
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EndpointFactory.class);

    /**
     * Returns a basic {@link DocumentUploadEndpoint} instance
     * @param baseUrl Base url of the api
     * @return Basic document upload endpoint
     */
    @NotNull
    @Contract("_ -> new")
    public static DocumentUploadEndpoint documentUploadEndpoint(@NotNull String baseUrl) {
        logger.debug("Creating DocumentUploadEndpoint");
        return new DocumentUploadEndpoint(baseUrl);
    }

    /**
     * Returns a basic {@link DocumentDownloadEndpoint} instance
     * @param baseUrl Base url of the api
     * @return Basic document download endpoint
     */
    @NotNull
    @Contract("_ -> new")
    public static DocumentDownloadEndpoint documentDownloadEndpoint(@NotNull String baseUrl) {
        logger.debug("Creating DocumentDownloadEndpoint");
        return new DocumentDownloadEndpoint(baseUrl);
    }

    /**
     * Returns a basic {@link DocumentEndpoint} instance
     * @param baseUrl Base url of the api
     * @return Basic document endpoint
     */
    @NotNull
    @Contract("_ -> new")
    public static DocumentEndpoint documentEndpoint(@NotNull String baseUrl) {
        logger.debug("Creating DocumentDownloadEndpoint");
        return new DocumentEndpoint(baseUrl);
    }

    /**
     * Returns a {@link SingleCustomFieldEndpoint} with {@link HttpMethodC#GET} already set.
     * @param baseUrl Base url of paperless ngx api
     * @return {@link SingleCustomFieldEndpoint} with {@link HttpMethodC#GET} already set.
     */
    @NotNull
    public static SingleCustomFieldEndpoint getCustomField(@NotNull String baseUrl) {
        var ep = new SingleCustomFieldEndpoint(baseUrl);
        ep.method(HttpMethodC.GET);
        return ep;
    }

    /**
     * Returns a {@link SingleCustomFieldEndpoint} with {@link HttpMethodC#PATCH} already set.
     * @param baseUrl Base url of paperless ngx api
     * @return {@link SingleCustomFieldEndpoint} with {@link HttpMethodC#PATCH} already set.
     */
    @NotNull
    public static SingleCustomFieldEndpoint updateCustomField(@NotNull String baseUrl) {
        var ep = new SingleCustomFieldEndpoint(baseUrl);
        ep.method(HttpMethodC.PATCH);
        return ep;
    }

    /**
     * Returns a {@link SingleCustomFieldEndpoint} with {@link HttpMethodC#DELETE} already set.
     * @param baseUrl Base url of paperless ngx api
     * @return {@link SingleCustomFieldEndpoint} with {@link HttpMethodC#DELETE} already set.
     */
    @NotNull
    public static SingleCustomFieldEndpoint deleteCustomField(@NotNull String baseUrl) {
        var ep = new SingleCustomFieldEndpoint(baseUrl);
        ep.method(HttpMethodC.DELETE);
        return ep;
    }

    /**
     * Returns a {@link SingleStoragepathEndpoint} with {@link HttpMethodC#GET} already set.
     * @param baseUrl Base url of paperless ngx api
     * @return {@link SingleStoragepathEndpoint} with {@link HttpMethodC#GET} already set.
     */
    @NotNull
    public static SingleStoragepathEndpoint getStoragepath(@NotNull String baseUrl) {
        var ep = new SingleStoragepathEndpoint(baseUrl);
        ep.method(HttpMethodC.GET);
        return ep;
    }

    /**
     * Returns a {@link SingleStoragepathEndpoint} with {@link HttpMethodC#PATCH} already set.
     * @param baseUrl Base url of paperless ngx api
     * @return {@link SingleStoragepathEndpoint} with {@link HttpMethodC#PATCH} already set.
     */
    @NotNull
    public static SingleStoragepathEndpoint updateStoragepath(@NotNull String baseUrl) {
        var ep = new SingleStoragepathEndpoint(baseUrl);
        ep.method(HttpMethodC.PATCH);
        return ep;
    }

    /**
     * Returns a {@link SingleStoragepathEndpoint} with {@link HttpMethodC#DELETE} already set.
     * @param baseUrl Base url of paperless ngx api
     * @return {@link SingleStoragepathEndpoint} with {@link HttpMethodC#DELETE} already set.
     */
    @NotNull
    public static SingleStoragepathEndpoint deleteStoragepath(@NotNull String baseUrl) {
        var ep = new SingleStoragepathEndpoint(baseUrl);
        ep.method(HttpMethodC.DELETE);
        return ep;
    }

}
