package org.dmsextension.paperless.client.endpoints;

public abstract class ParameterEndpoint extends PaperlessEndpoint implements IParametrizedEndpoint {

    protected ParameterEndpoint(String baseUrl) {
        super(baseUrl);
    }
}
