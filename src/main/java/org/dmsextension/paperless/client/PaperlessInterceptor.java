package org.dmsextension.paperless.client;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Default Paperless-ngx authentication interceptor. Gets the token for given user and password
 * from the paperless api and adds the Authorization header to all calls.
 */
public class PaperlessInterceptor implements Interceptor {
    /**
     * Loggern
     */
    private final Logger logger = LoggerFactory.getLogger(PaperlessInterceptor.class);
    /**
     * User
     */
    private String user;
    /**
     * Password
     */
    private String pw;
    /**
     * Base url
     */
    private final String baseUrl;
    /**
     * Cache of all already retrieved tokens
     */
    private static final HashMap<String, String> tokens = new HashMap<>();

    /**
     * Default constructor
     * @param user User to authenticate at the paperless api
     * @param pw Password to authenticate at the paperless api
     * @param baseUrl Base url of the paperless api
     */
    public PaperlessInterceptor(String user, String pw, String baseUrl) {
        this.pw = pw;
        this.user = user;
        this.baseUrl = baseUrl;
    }

    /**
     * Convenience class for parsing token responses
     */
    private static class Token {
        public String token;
    }

    /**
     * Method to intercept the api calls and add Authorization token
     * @param chain Chain of api call
     * @return Modified chain with Authorization header
     * @throws IOException Thrown, if no api token could be fetched
     */
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (tokens.get(this.user) == null) {
            String json = String.format("{ \"username\": \"%s\",\"password\": \"%s\" }",
                    this.user,
                    this.pw
            );
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(
                            this.baseUrl + "token/"
                    )
                    .header("Content-Type", "application/json")
                    .post(RequestBody.create(json, MediaType.parse("application/json")))
                    .build();

            try (Response r = client.newCall(request).execute()) {
                if (r.isSuccessful()) {
                    logger.debug(String.format("Request successful, getting token for user %s", this.user));
                    JsonAdapter<Token> adapter = (new Moshi.Builder().build()).adapter(Token.class);
                    tokens.put(this.user, Objects.requireNonNull(adapter.fromJson(r.body().string())).token);
                } else {
                    this.logger.info("Could not get api token");
                    throw new IOException("API token not fetched");
                }
            } catch (Exception ex) {
                this.logger.info("Exception in api call: " + ex);
                throw ex;
            }
        }
        Request req = chain.request()
                .newBuilder()
                .header("Authorization", "Token " + tokens.get(this.user))
                .build();
        return chain.proceed(req);
    }

    /**
     * Gets user for authentication
     * @return Username
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets user for authentication
     * @param user Username
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets password for authentication
     * @return Password
     */
    public String getPw() {
        return pw;
    }

    /**
     * Sets password for authentication
     * @param pw Password
     */
    public void setPw(String pw) {
        this.pw = pw;
    }
}
