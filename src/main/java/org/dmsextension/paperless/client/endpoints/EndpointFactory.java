package org.dmsextension.paperless.client.endpoints;

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
}
