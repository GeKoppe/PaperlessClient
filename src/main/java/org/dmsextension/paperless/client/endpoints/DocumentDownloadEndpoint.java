package org.dmsextension.paperless.client.endpoints;

import com.github.jknack.handlebars.Template;
import okhttp3.Request;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.PaperlessActionC;
import org.dmsextension.paperless.client.http.HttpMethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TDocumentDownload;
import org.dmsextension.paperless.client.utils.CustomHandlebars;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Endpoint for downloading files from paperless
 */
public class DocumentDownloadEndpoint extends ParameterEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(DocumentDownloadEndpoint.class);
    /**
     * Methods
     */
    private static final List<HttpMethodC> methods = new ArrayList<>(List.of(HttpMethodC.GET));
    /**
     * List of all url params
     */
    private static final List<String> params = new ArrayList<>(List.of("id"));
    /**
     * Url parameters. Value associated to each key is templated into the url
     */
    private Map<String, String> urlParams;
    /**
     * Resource for this endpoint
     */
    private static final String endpoint = "documents/{{id}}/download/";

    /**
     * Default constructor
     * @param baseUrl Base url of paperless api
     */
    public DocumentDownloadEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * {@inheritDoc}
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest() throws Exception {
        if (this.urlParams == null || ! IParametrizedEndpoint.allPathParamsGiven(this.urlParams, params)) {
            this.logger.info("No url params or not all needed were given prior to building request");
            throw new IllegalStateException("No url params or not all needed were given prior to building request");
        }
        String completedUlr = this.getBaseUrl() + endpoint;

        Request.Builder builder = new Request.Builder();
        CustomHandlebars handlebars = new CustomHandlebars();
        Template t = handlebars.compileInline(completedUlr);
        builder.url(t.apply(this.urlParams) + this.parseQuery())
                .get();
        return builder.build();
    }

    /**
     * {@inheritDoc}
     * This endpoint does not support methods other than GET, therefore this method just calls {@link DocumentDownloadEndpoint#buildRequest()}.
     * @param body Body to send. If configured operation of the endpoint does not require a body, {@link IEndpoint#buildRequest()}
     *             is called instead
     * @return
     * @throws Exception
     */
    @Override
    public Request buildRequest(@NotNull IDto body) throws Exception {
        this.logger.debug(String.format("Endpoint %s does not support methods with bodies, using buildRequest instead", this));
        return buildRequest();
    }

    /**
     * {@inheritDoc}
     * @param response Response of the api call
     * @return The file, wrapped into a {@link TDocumentDownload} object. Call {@link TDocumentDownload#getFile()}
     * to retrieve the actual file.
     * @throws Exception
     */
    @Override
    public TDocumentDownload parseResponse(@NotNull Response response) throws Exception {
        this.logger.debug("Parsing reponse");

        String fileName = response.header("content-disposition");
        if (fileName == null || response.body() == null) {
            this.logger.debug("No content-disposition or body, invalid response");
            throw new RuntimeException();
        }
        fileName = fileName.substring(fileName.indexOf("filename"));
        fileName = fileName.substring(fileName.indexOf("\"") + 1, fileName.indexOf(";") - 1);
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);

        this.logger.debug("Extracting file from input stream");
        InputStream is = response.body().byteStream();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buf = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
        }
        this.logger.debug("File extracted");

        TDocumentDownload tdd = new TDocumentDownload();
        tdd.setFile(file);
        return tdd;
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
     * This particular endpoint only supports {@link PaperlessActionC#DOWNLOAD}, therefore this method is not usable.
     * @param action Action the endpoint should execute
     */
    @Override
    public void action(@NotNull PaperlessActionC action) {
        this.logger.debug("Endpoint " + this + " only supports DOWNLOAD, not setting");
    }

    /**
     * {@inheritDoc}
     * This particular endpoint only supports {@link HttpMethodC#GET}, therefore this method is not usable.
     * @param method HTTP Method
     */
    @Override
    public void method(@NotNull HttpMethodC method) {
        this.logger.debug("Endpoint " + this + " only supports DOWNLOAD, not setting");
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getPathParams() {
        return params;
    }

    /**
     * {@inheritDoc}
     * @param params Params for the url
     */
    @Override
    public void pathParams(Map<String, String> params) {
        this.urlParams = params;
    }
}
