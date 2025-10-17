package org.dmsextension.paperless.client;

import okhttp3.*;
import org.dmsextension.paperless.client.endpoints.*;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class are capable of communicating with Paperless-ngx.
 * Certain convenience methods like {@link PaperlessClient#uploadDocument(File)} are already
 * implemented, you can also instantiate your own {@link IEndpoint}
 * class and call {@link PaperlessClient#execute(IEndpoint, IDto)} and configure the call yourself.
 */
public class PaperlessClient {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(PaperlessClient.class);
    /**
     * Base url of the api
     */
    private final String url;
    /**
     * Client for calling the api
     */
    private final OkHttpClient httpClient;
    /**
     * Default constructor
     * @param interceptor Interceptor for authentication on the paperless-ngx api
     * @param url Base url of the paperless-ngx api
     */
    private PaperlessClient(Interceptor interceptor, String url) {
        this.httpClient = new OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        this.url = url;
    }

    /**
     * Executes api call with given endpoint
     * @param endpoint Endpoint to call
     * @return Result of the endpoint call
     */
    public IDto execute(IEndpoint endpoint, @Nullable IDto body) throws Exception {
        IDto response;
        this.logger.debug("Calling api with given endpoint and body");
        Request request = body == null ? endpoint.buildRequest() : endpoint.buildRequest(body);
        try (Response r = this.httpClient.newCall(request).execute()) {
            if (r.isSuccessful()) {
                this.logger.debug("Api call successful, parsing response");
                response = endpoint.parseResponse(r);
            } else {
                this.logger.info("API call not successful: " + r.message());
                response = null;
            }
        } catch (Exception ex) {
            this.logger.info("Exception while querying api: " + ex);
            throw ex;
        }
        return response;
    }

    public TSpecifiedSearchResult<TDocument> executeDocumentPaginated(Map<String, String> query) throws Exception {
        TSpecifiedSearchResult<TDocument> fullResponse = null;
        this.logger.debug("Calling api with given endpoint and body");
        DocumentEndpoint endpoint = EndpointFactory.documentEndpoint(this.getUrl());
        if (query == null) {
            query = new HashMap<>();

        }
        query.put("page", "1");
        endpoint.query(query);

        Request request = endpoint.buildRequest();
        try (Response r = this.httpClient.newCall(request).execute()) {
            if (r.isSuccessful()) {
                this.logger.debug("Api call successful, parsing response");
                fullResponse = endpoint.parseResponse(r);
            } else {
                this.logger.info("API call not successful: " + r.message());
                return null;
            }
        } catch (Exception ex) {
            this.logger.info("Exception while querying api: " + ex);
            throw ex;
        }
        int page = 2;
        while (fullResponse.getNext() != null) {
            query.put("page", "" + page);
            endpoint.query(query);
            Request req = endpoint.buildRequest();
            try (Response r = this.httpClient.newCall(req).execute()) {
                if (r.isSuccessful()) {
                    this.logger.debug("Api call successful, parsing response");
                    TSpecifiedSearchResult<TDocument> response = endpoint.parseResponse(r);
                    fullResponse.getResults().addAll(response.getResults());
                    fullResponse.setNext(response.getNext());
                } else {
                    this.logger.info("API call not successful: " + r.message());
                }
            } catch (Exception ex) {
                this.logger.info("Exception while querying api: " + ex);
                throw ex;
            }
            page++;
        }
        return fullResponse;
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

    /**
     * Convenience method for uploading documents with given metadata set
     * @param file Document to upload to paperless-ngx
     * @param metadata Metadata to set on uploaded document
     * @return Guid of the uploaded document
     */
    public String uploadDocument(@NotNull File file, @NotNull HashMap<String, Objects> metadata) {
        String uploadedId = "";
        this.logger.debug(String.format("Uploading file %s to paperless", file));

        var endpoint = EndpointFactory.documentUploadEndpoint(this.url);
        endpoint.method(MethodC.POST);
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

    /**
     * Downloads document with given documentId from paperless-ngx by utilizing
     * {@link DocumentDownloadEndpoint}.
     * @param documentId ID of the document
     * @return Downloaded file
     * @throws Exception When shit goes down.
     */
    public File downloadDocument(String documentId) throws Exception {
        var endpoint = EndpointFactory.documentDownloadEndpoint(this.url);

        Map<String, String> map = new HashMap<>(Map.of("id", documentId));
        endpoint.pathParams(map);

        Request req = endpoint.buildRequest();
        TDocumentDownload result = null;
        try (Response r = this.httpClient.newCall(req).execute()) {
            if (r.isSuccessful()) {
                this.logger.debug("Request successful, parsing response");
                result = endpoint.parseResponse(r);
            } else {
                this.logger.debug("Request not successful");
            }
        } catch (Exception ex) {
            this.logger.debug("Exception occurred while parsing response: " + ex);
        }

        return result != null ? result.getFile() : null;
    }

    /**
     * Retrieves document with given id by using a {@link SingleDocumentEndpoint}
     * @param id ID of document to retrieve
     * @return TDocument instance for given id or null, if id does not exist in dms
     * @throws Exception When shit went seriously wrong
     */
    public TDocument getDocument(String id) throws Exception {
        SingleDocumentEndpoint ep = new SingleDocumentEndpoint(this.url);
        ep.pathParams(Map.of("id", id));
        ep.method(MethodC.GET);
        IDto result = this.execute(ep, null);
        return result == null ? null : (TDocument) result;
    }

    /**
     * Get base url of the paperless-ngx api this client is supposed to call
     * @return Base url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Builder class for {@link PaperlessClient} objects.
     * Following methods must be called in order for building to be successful:
     * - either {@link Builder#user(String)} and {@link Builder#password(String)} or {@link Builder#interceptor(Interceptor)}
     * - either {@link Builder#protocol(String)} and {@link Builder#host(String)} and {@link Builder#port(String)} or {@link Builder#url(String)}
     */
    public static class Builder {
        /**
         * Logger
         */
        private final Logger logger = LoggerFactory.getLogger(Builder.class);
        /**
         * Paperless url
         */
        private String url;
        /**
         * Paperless port
         */
        private String port;
        /**
         * Paperless protocol
         */
        private String protocol;
        /**
         * Paperless host
         */
        private String host;
        /**
         * Paperless user
         */
        private String user;
        /**
         * Paperless password
         */
        private String pw;
        /**
         * Authentication interceptor. Adds Authorization header to paperless api calls
         */
        private Interceptor interceptor;
        /**
         * Used for keeping track of information added to the builder
         */
        private final HashMap<String, Boolean> informationAdded = new HashMap<>();

        /**
         * Default constructor
         */
        public Builder() {
            informationAdded.put("url", false);
            informationAdded.put("port", false);
            informationAdded.put("protocol", false);
            informationAdded.put("host", false);
            informationAdded.put("user", false);
            informationAdded.put("pw", false);
            informationAdded.put("interceptor", false);
        }

        /**
         * Builds the {@link PaperlessClient}
         * @return Built {@link PaperlessClient}
         * @throws IllegalStateException Thrown, if not all necessary information has been added to builder
         */
        public PaperlessClient build() throws IllegalStateException {
            if (!this.informationAdded.get("url")) {
                this.logger.debug("No url set, trying to build url with protocol, host and port");
                if (this.informationAdded.get("port") && this.informationAdded.get("host") && this.informationAdded.get("protocol")) {
                    this.url = String.format("%s://%s:%s/api/", this.protocol, this.host, this.port);
                    this.informationAdded.put("url", true);
                    this.logger.debug(String.format("Built url: %s", this.url));
                }
            }

            if (!this.informationAdded.get("interceptor")) {
                if (this.informationAdded.get("user") && this.informationAdded.get("pw")) {
                    this.interceptor = new PaperlessInterceptor(this.user, this.pw, this.url);
                    this.informationAdded.put("interceptor", true);
                }
            }
            if (this.informationAdded.containsValue(false)) {
                this.logger.info("Missing values for building paperless client");
                throw new IllegalStateException("Missing information");
            }

            return new PaperlessClient(this.interceptor, this.url);
        }

        /**
         * Add interceptor to Builder. Interceptor should add a valid Authorization header to the api call.
         * If interceptor is given {@link Builder#user(String)} and {@link Builder#password(String)} do not need
         * to be called.
         * @param interceptor Interceptor to add
         * @return Builder
         */
        public Builder interceptor(@NotNull Interceptor interceptor) {
            this.informationAdded.put("interceptor", true);
            this.informationAdded.put("user", true);
            this.informationAdded.put("pw", true);
            this.interceptor = interceptor;
            return this;
        }

        /**
         * Adds url to builder. If this method is called, {@link Builder#protocol(String)}, {@link Builder#host(String)}},
         * and {@link Builder#port(String)}, must not be called.
         * @param url URL of the paperless-ngx api.
         * @return Builder
         */
        public Builder url(@NotNull String url) {
            this.url = url;
            this.informationAdded.put("url", true);
            this.informationAdded.put("host", true);
            this.informationAdded.put("port", true);
            this.informationAdded.put("protocol", true);
            return this;
        }

        /**
         * Adds protocol to builder
         * @param protocol Protocol of paperless-ngx api
         * @return Builder
         */
        public Builder protocol(@NotNull String protocol) {
            this.protocol = protocol;
            this.informationAdded.put("protocol", true);
            return this;
        }

        /**
         * Adds port to builder
         * @param port Port of paperless-ngx api
         * @return Builder
         */
        public Builder port(@NotNull String port) {
            this.port = port;
            this.informationAdded.put("port", true);
            return this;
        }

        /**
         * Adds port to builder
         * @param port Port of paperless-ngx api
         * @return Builder
         */
        public Builder port(int port) {
            this.port = "" + port;
            this.informationAdded.put("port", true);
            return this;
        }

        /**
         * Adds host to builder
         * @param host Host of paperless-ngx api
         * @return Builder
         */
        public Builder host(@NotNull String host) {
            this.host = host;
            this.informationAdded.put("host", true);
            return this;
        }

        /**
         * Adds user to builder
         * @param user User to authenticate at the paperless-ngx api
         * @return Builder
         */
        public Builder user(@NotNull String user) {
            this.user = user;
            this.informationAdded.put("user", true);
            return this;
        }

        /**
         * Adds password to builder
         * @param pw Password to authenticate at the paperless-ngx api
         * @return Builder
         */
        public Builder password(@NotNull String pw) {
            this.pw = pw;
            this.informationAdded.put("pw", true);
            return this;
        }
    }
}
