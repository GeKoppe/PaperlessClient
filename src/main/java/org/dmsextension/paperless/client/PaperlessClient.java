package org.dmsextension.paperless.client;

import okhttp3.*;
import org.dmsextension.paperless.client.endpoints.EndpointFactory;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.TDocumentUpload;
import org.dmsextension.paperless.client.templates.TDocumentUploadResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PaperlessClient {
    private final Logger logger = LoggerFactory.getLogger(PaperlessClient.class);
    private String url;
    private OkHttpClient httpClient;

    private PaperlessClient(String user, String pw, String url) {
        this.httpClient = new OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new PaperlessInterceptor(user, pw, url))
                .build();
        this.url = url;
    }

    /**
     * Uploads a file to paperless. Calls {@link PaperlessClient#uploadDocument(File, HashMap)} with an empty hashmap,
     * therefore the file will be uploaded without a metadataset, just the file with the filename as title.
     * @param file File to upload to Paperless
     * @return Guid of the uploaded file
     */
    public String uploadDocument(@NotNull File file) {
        return this.uploadDocument(file, new HashMap<>());
    }

    public String uploadDocument(@NotNull File file, HashMap<String, Objects> metadata) {
        String uploadedId = "";
        this.logger.debug(String.format("Uploading file %s to paperless", file));

        var endpoint = EndpointFactory.documentUploadEndpoint(this.url);
        endpoint.setMethod(MethodC.POST);
        var docUp = new TDocumentUpload();
        docUp.setDocument(file);
        this.logger.debug("Built endpoint and template");

        Request req = endpoint.buildRequest(docUp);
        this.logger.debug("Built request, executing");
        try (Response r = this.httpClient.newCall(req).execute()) {
            if (r.isSuccessful()) {
                uploadedId = endpoint.parseResponse(r).getGuid();
                this.logger.debug(String.format("Request successful, uploaded document, guid: %s", uploadedId));
            } else {
                this.logger.debug("Request not successful");
            }
        } catch (Exception ex) {
            this.logger.info("Exception occurred while executing request: " + ex);
        }

        return uploadedId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class Builder {
        private final Logger logger = LoggerFactory.getLogger(Builder.class);
        private PaperlessClient client;
        private String url;
        private String port;
        private String protocol;
        private String host;
        private String user;
        private String pw;
        private final HashMap<String, Boolean> informationAdded = new HashMap<>();

        public Builder() {
            informationAdded.put("url", false);
            informationAdded.put("port", false);
            informationAdded.put("protocol", false);
            informationAdded.put("host", false);
            informationAdded.put("user", false);
            informationAdded.put("pw", false);
        }

        public PaperlessClient build() throws IllegalStateException {
            if (!this.informationAdded.get("url")) {
                if (this.informationAdded.get("port") && this.informationAdded.get("host") && this.informationAdded.get("protocol")) {
                    this.url = String.format("%s://%s:%s/api/", this.protocol, this.host, this.port);
                    this.informationAdded.put("url", true);
                }
            }
            if (this.informationAdded.containsValue(false)) {
                this.logger.info("Missing values for building paperless client");
                throw new IllegalStateException("Missing information");
            }

            this.client = new PaperlessClient(this.user, this.pw, this.url);
            return this.client;
        }
        public Builder url(String url) {
            this.url = url;
            this.informationAdded.put("url", true);
            this.informationAdded.put("host", true);
            this.informationAdded.put("port", true);
            this.informationAdded.put("protocol", true);
            return this;
        }

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            this.informationAdded.put("protocol", true);
            return this;
        }

        public Builder port(String port) {
            this.port = port;
            this.informationAdded.put("port", true);
            return this;
        }

        public Builder port(int port) {
            this.port = "" + port;
            this.informationAdded.put("port", true);
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            this.informationAdded.put("host", true);
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            this.informationAdded.put("user", true);
            return this;
        }

        public Builder password(String pw) {
            this.pw = pw;
            this.informationAdded.put("pw", true);
            return this;
        }
    }
}
