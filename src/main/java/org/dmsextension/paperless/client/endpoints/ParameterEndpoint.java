package org.dmsextension.paperless.client.endpoints;

import com.github.jknack.handlebars.Template;
import org.dmsextension.paperless.client.utils.CustomHandlebars;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
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
    private Map<String, String> params;
    private String endpoint;

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

    protected String parseEndpoint(String endpoint) throws IOException {
        Template template = this.handlebars.compileInline(this.getBaseUrl() + endpoint);
        return template.apply(this.params);
    }

    public Map<String, String> getParams() {
        return params;
    }



    public String getEndpoint() {
        return endpoint;
    }

}
