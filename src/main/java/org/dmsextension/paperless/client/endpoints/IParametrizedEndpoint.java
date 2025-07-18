package org.dmsextension.paperless.client.endpoints;

import java.util.List;
import java.util.Map;

/**
 * Implemented by endpoint classes, that use a url with parameters.
 * If, for example, the endpoint is /documents/<b>{id}</b>/update, the class
 * has to implement this interface, in order to correctly build the url
 */
public interface IParametrizedEndpoint extends IEndpoint {
    /**
     * Used to get a list of all the parameters in the url
     * @return List of all parameters in url
     */
    List<String> getPathParams();

    /**
     * Adds parameters for the url to the class
     * @param params Params for the url
     */
    void pathParams(Map<String, String> params);

    /**
     * Checks if all values in needed are keys in params
     * @param params Actual path params
     * @param needed Needed path params
     * @return True, if all params are given, false otherwise
     */
    static boolean allPathParamsGiven(Map<String, String> params, List<String> needed) {
        return params.keySet().containsAll(needed);
    }
}
