package org.dmsextension.paperless.client.endpoints;

import okhttp3.Request;
import okhttp3.Response;
import org.dmsextension.paperless.client.http.ActionC;
import org.dmsextension.paperless.client.http.MethodC;
import org.dmsextension.paperless.client.templates.IDto;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public interface IEndpoint {
    /**
     * Method used for building request, that does not require a body. If this method is called for a
     * {@link PaperlessEndpoint} instance with a method that requires a body, usually an exception is thrown.
     * @return The built request
     * @throws Exception When this method is called for operations that require a body
     */
    Request buildRequest() throws Exception;
    Request buildRequest(@NotNull IDto body) throws Exception;
    IDto parseResponse(@NotNull Response response) throws Exception;
    List<MethodC> getMethods();
    void action(@NotNull ActionC action);
    void method(@NotNull MethodC method);
    // IDto bodyFromParams(HashMap<String, Object> params);
}
