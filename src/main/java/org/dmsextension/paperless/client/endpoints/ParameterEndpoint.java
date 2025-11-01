package org.dmsextension.paperless.client.endpoints;

import com.github.jknack.handlebars.Template;
import org.dmsextension.paperless.client.utils.CustomHandlebars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Extends {@link PaperlessEndpoint} with possibilities to parse dynamic path endpoints.
 */
public abstract class ParameterEndpoint extends PaperlessEndpoint implements IParametrizedEndpoint {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(ParameterEndpoint.class);
    /**
     * Handlebars
     */
    private final CustomHandlebars handlebars = new CustomHandlebars();
    /**
     * Map with all url params
     */
    private Map<String, String> params;
    /**
     * Default constructor
     * @param baseUrl Paperless api base url
     */
    protected ParameterEndpoint(String baseUrl) {
        super(baseUrl);
    }

    /**
     * Returns the handlebar instance
     * @return Handlebars instance
     */
    protected CustomHandlebars getHandlebars() { return this.handlebars; }

    /**
     * {@inheritDoc}
     * @param params Params for the url
     */
    @Override
    public void pathParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Uses {@link CustomHandlebars} to compile the base url, concatinated with parametrized endpoint url to an
     * actual url, using {@link ParameterEndpoint#params}.
     * @param endpoint Endpoint to compile
     * @return Complete url of the endpoint
     * @throws IOException If handlebars can't compile the string.
     */
    protected String parseEndpoint(String endpoint) throws IOException {
        logger.info("Templating endpoint with path params");
        Template template = this.handlebars.compileInline(this.getBaseUrl() + endpoint);
        return template.apply(this.params);
    }

    /**
     * Returns all params already given to the class
     * @return Params
     */
    public Map<String, String> getParams() {
        return params;
    }

}
