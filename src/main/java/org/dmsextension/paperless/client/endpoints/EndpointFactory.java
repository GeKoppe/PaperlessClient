package org.dmsextension.paperless.client.endpoints;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointFactory {
    private static final Logger logger = LoggerFactory.getLogger(EndpointFactory.class);

    @NotNull
    @Contract("_ -> new")
    public static DocumentUploadEndpoint documentUploadEndpoint(@NotNull String baseUrl) {
        return new DocumentUploadEndpoint(baseUrl);
    }

    public static DocumentDownloadEndpoint documentDownloadEndpoint(@NotNull String baseUrl) {
        return new DocumentDownloadEndpoint(baseUrl);
    }
}
